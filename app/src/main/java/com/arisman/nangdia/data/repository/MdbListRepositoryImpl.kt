package com.arisman.nangdia.data.repository

import com.arisman.nangdia.data.mapper.*
import com.arisman.nangdia.data.remote.MdbListRemoteDataSource
import com.arisman.nangdia.domain.model.MediaDetail
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.domain.model.PersonDetail
import com.arisman.nangdia.domain.repository.MdbListRepository
import com.arisman.nangdia.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MdbListRepositoryImpl @Inject constructor(
    private val remoteDataSource: MdbListRemoteDataSource
) : MdbListRepository {

    override fun searchMedia(
        query: String,
        type: String?,
        year: String?
    ): Flow<Resource<List<MediaSearchResult>>> = flow {
        emit(Resource.Loading())

        val tmdbResponse = when (type) {
            "movie" -> remoteDataSource.searchTmdbMovie(query, 1, year)
            "show" -> remoteDataSource.searchTmdbTv(query, 1, year)
            else -> remoteDataSource.searchTmdb(query, 1) // Multi-search doesn't support year filter efficiently
        }

        if (tmdbResponse is Resource.Success) {
            val results = tmdbResponse.data?.results
                ?.filter {
                    val matchesType = type == null || it.mediaType == type || (type == "show" && it.mediaType == "tv")
                    it.mediaType != "person" && matchesType
                }
                ?.map { it.toMediaSearchResult() } ?: emptyList()
            emit(Resource.Success(results))
        } else {
            // Fallback to MDBList if TMDB search fails
            val response = remoteDataSource.searchMdb(query = query, page = 1, type = type, year = year)
            when (response) {
                is Resource.Success -> {
                    val results = response.data?.search
                        ?.filter {
                            val matchesType = type == null || it.type == type || (type == "show" && it.type == "tv")
                            it.type != "person" && matchesType
                        }
                        ?.map { it.toMediaSearchResult() } ?: emptyList()
                    emit(Resource.Success(results))
                }
                is Resource.Error -> {
                    emit(Resource.Error(response.message ?: "Unknown Error"))
                }
                is Resource.Loading -> {}
            }
        }
    }

    override fun getMediaDetail(
        id: String,
        mediaType: String,
        provider: String
    ): Flow<Resource<MediaDetail>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getMediaDetails(id, mediaType, provider)

        var currentDetail: MediaDetail? = null

        if (response is Resource.Success && response.data != null) {
            currentDetail = response.data.toMediaDetail()
        } else if (provider == "tmdb") {
            // If MDBList fails for a TMDB ID, try to bootstrap from TMDB detail directly
            val tmdbId = id.toIntOrNull()
            if (tmdbId != null) {
                val tmdbResponse = remoteDataSource.getTmdbDetail(tmdbId, mediaType)
                if (tmdbResponse is Resource.Success && tmdbResponse.data != null) {
                    // Start with a basic MediaDetail from TMDB data
                    currentDetail = MediaDetail(
                        title = tmdbResponse.data.title ?: tmdbResponse.data.name ?: "Unknown",
                        year = tmdbResponse.data.releaseDate?.take(4)?.toIntOrNull() ?: tmdbResponse.data.firstAirDate?.take(4)?.toIntOrNull(),
                        description = tmdbResponse.data.overview,
                        mediaType = mediaType,
                        tmdbId = tmdbId,
                        poster = if (tmdbResponse.data.posterPath.isNullOrEmpty()) null else "https://image.tmdb.org/t/p/w500${tmdbResponse.data.posterPath}",
                        backdrop = if (tmdbResponse.data.backdropPath.isNullOrEmpty()) null else "https://image.tmdb.org/t/p/original${tmdbResponse.data.backdropPath}",
                        genres = tmdbResponse.data.genres.mapNotNull { it.name },
                        status = tmdbResponse.data.status,
                        popularity = tmdbResponse.data.popularity ?: 0.0,
                        scoreAverage = tmdbResponse.data.voteAverage ?: 0.0,
                        voteCount = tmdbResponse.data.voteCount ?: 0,
                        imdbId = tmdbResponse.data.imdbId
                    )
                }
            }
        }

        if (currentDetail != null) {
            var detail = currentDetail!!
            var tmdbData: com.arisman.nangdia.data.remote.dto.TmdbDetailResponse? = null

            // Try to fetch additional TMDB info even if we already have MDBList data
            if (detail.tmdbId != null) {
                val tmdbResponse = remoteDataSource.getTmdbDetail(detail.tmdbId, detail.mediaType ?: "movie")
                if (tmdbResponse is Resource.Success && tmdbResponse.data != null) {
                    tmdbData = tmdbResponse.data

                    // Map Genres if missing
                    currentDetail = detail.copy(
                        tmdbId = tmdbData.id ?: detail.tmdbId,
                        title = detail.title.ifEmpty { tmdbData.name ?: tmdbData.title ?: detail.title },
                        description = detail.description.takeIf { !it.isNullOrEmpty() } ?: tmdbData.overview,
                        cast = tmdbData.credits?.cast?.take(20)?.map { it.toMediaCast() } ?: detail.cast,
                        recommendations = tmdbData.recommendations?.results?.map { it.toMediaSearchResult() } ?: detail.recommendations,
                        popularity = detail.popularity ?: tmdbData.popularity,
                        seasons = tmdbData.seasons.map { it.toMediaSeason() }
                    )
                    detail = currentDetail

                    // Map Genres if missing
                    if (detail.genres.isEmpty()) {
                        val genres = tmdbData.genres.mapNotNull { it.name }
                        if (genres.isNotEmpty()) {
                            detail = detail.copy(genres = genres)
                        }
                    }

                    // Map Recommendations
                    val recommendations = tmdbData.recommendations?.results?.map { it.toMediaSearchResult() } ?: emptyList()
                    detail = detail.copy(recommendations = recommendations)

                    // Map Credits/Cast
                    val cast = tmdbData.credits?.cast?.take(15)?.map { it.toMediaCast() } ?: emptyList()
                    detail = detail.copy(cast = cast)

                    // Map Popularity if missing or 0
                    if (detail.popularity == null || detail.popularity == 0.0) {
                        detail = detail.copy(popularity = tmdbData.popularity)
                    }
                }
            }

            // Fetch IMDb-specific data (parental guide, awards) if IMDb ID is available
            val imdbId = detail.imdbId
            if (!imdbId.isNullOrEmpty()) {
                val pgResponse = remoteDataSource.getParentsGuide(imdbId)
                if (pgResponse is Resource.Success && pgResponse.data != null) {
                    val categories = pgResponse.data.parentsGuide.map { categoryDto ->
                        var maxSeverityLabel = "none"
                        var maxVotes = -1
                        categoryDto.severityBreakdowns.forEach { breakdown ->
                            if (breakdown.voteCount > maxVotes) {
                                maxVotes = breakdown.voteCount
                                maxSeverityLabel = breakdown.severityLevel
                            }
                        }
                        com.arisman.nangdia.domain.model.ParentalGuideCategory(
                            category = categoryDto.category,
                            severity = maxSeverityLabel,
                            voteCount = maxVotes,
                            reviews = categoryDto.reviews.map { it.text }
                        )
                    }
                    detail = detail.copy(parentalGuideCategories = categories)
                }

                val awardsResponse = remoteDataSource.getAwards(imdbId)
                if (awardsResponse is Resource.Success && awardsResponse.data?.stats != null) {
                    val stats = awardsResponse.data.stats
                    detail = detail.copy(
                        awardWins = stats.winCount,
                        awardNominations = stats.nominationCount
                    )
                }
            }

            // Final fallback for certification
            if (detail.parentalGuideCategories.isEmpty() && tmdbData != null) {
                var certification: String? = null
                if (detail.mediaType == "movie" && tmdbData.releaseDates != null) {
                    val usRelease = tmdbData.releaseDates.results.find { it.iso == "US" } ?: tmdbData.releaseDates.results.firstOrNull()
                    certification = usRelease?.releaseDates?.firstOrNull()?.certification
                } else if ((detail.mediaType == "show" || detail.mediaType == "tv") && tmdbData.contentRatings != null) {
                    val usRating = tmdbData.contentRatings.results.find { it.iso == "US" } ?: tmdbData.contentRatings.results.firstOrNull()
                    certification = usRating?.rating
                }

                if (!certification.isNullOrEmpty()) {
                    detail = detail.copy(parentalGuideCategories = listOf(
                        com.arisman.nangdia.domain.model.ParentalGuideCategory("Content Rating", certification, 0, emptyList())
                    ))
                }
            }
            emit(Resource.Success(detail))
        } else {
            val errorMsg = if (response is Resource.Error) response.message ?: "Detail Error" else "Media details not found"
            emit(Resource.Error(errorMsg))
        }
    }

    override fun getPopularMovies(): Flow<Resource<List<MediaSearchResult>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getMdbListItems(
            username = "snoak",
            slug = "todays-most-popular-movies"
        )
        when (response) {
            is Resource.Success -> {
                val results = response.data?.movies?.map { it.toMediaSearchResult() } ?: emptyList()
                emit(Resource.Success(results))
            }
            is Resource.Error -> {
                emit(Resource.Error(response.message ?: "MDBList Popular Movies Error: ${response.message}"))
            }
            is Resource.Loading -> {}
        }
    }

    override fun getPopularTvShows(): Flow<Resource<List<MediaSearchResult>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getMdbListItems(
            username = "snoak",
            slug = "trakt-s-trending-shows"
        )
        when (response) {
            is Resource.Success -> {
                val results = response.data?.shows?.map { it.toMediaSearchResult() } ?: emptyList()
                emit(Resource.Success(results))
            }
            is Resource.Error -> {
                emit(Resource.Error(response.message ?: "MDBList Popular Shows Error: ${response.message}"))
            }
            is Resource.Loading -> {}
        }
    }

    override fun getDiscoverLists(): Flow<Resource<List<com.arisman.nangdia.domain.model.MdbListMetadata>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getTopLists(limit = 20)
        when (response) {
            is Resource.Success -> {
                val results = response.data?.map { it.toMdbListMetadata() } ?: emptyList()
                emit(Resource.Success(results))
            }
            is Resource.Error -> {
                emit(Resource.Error(response.message ?: "MDBList Top Lists Error"))
            }
            is Resource.Loading -> {}
        }
    }

    override fun getMdbListMetadata(username: String, slug: String): Flow<Resource<com.arisman.nangdia.domain.model.MdbListMetadata>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getListMetadata(username, slug)
        when (response) {
            is Resource.Success -> {
                if (response.data != null) {
                    emit(Resource.Success(response.data.toMdbListMetadata()))
                } else {
                    emit(Resource.Error("List metadata not found"))
                }
            }
            is Resource.Error -> {
                emit(Resource.Error(response.message ?: "MDBList Metadata Error"))
            }
            is Resource.Loading -> {}
        }
    }

    override fun getMdbList(username: String, slug: String): Flow<Resource<List<MediaSearchResult>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getMdbListItems(username, slug)
        when (response) {
            is Resource.Success -> {
                val results = (response.data?.movies ?: emptyList()) + (response.data?.shows ?: emptyList())
                emit(Resource.Success(results.map { it.toMediaSearchResult() }))
            }
            is Resource.Error -> {
                emit(Resource.Error(response.message ?: "MDBList List Error: ${response.message}"))
            }
            is Resource.Loading -> {}
        }
    }

    override fun getPersonDetail(personId: Int): Flow<Resource<PersonDetail>> = flow {
        emit(Resource.Loading())
        val detailResponse = remoteDataSource.getPersonDetail(personId)
        val creditsResponse = remoteDataSource.getPersonCombinedCredits(personId)

        if (detailResponse is Resource.Success && detailResponse.data != null) {
            val credits = if (creditsResponse is Resource.Success && creditsResponse.data != null) {
                creditsResponse.data.cast
                    .filter { it.posterPath != null }
                    .sortedByDescending { it.releaseDate ?: it.firstAirDate }
                    .map { it.toPersonCastCredit() }
            } else {
                emptyList()
            }
            emit(Resource.Success(detailResponse.data.toPersonDetail(credits)))
        } else {
            emit(Resource.Error(detailResponse.message ?: "Failed to fetch person details"))
        }
    }

    override fun getTvSeasonDetail(tvId: Int, seasonNumber: Int): Flow<Resource<com.arisman.nangdia.domain.model.MediaSeason>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getTvSeasonDetail(tvId, seasonNumber)
        when (response) {
            is Resource.Success -> {
                val mediaSeason = response.data?.toMediaSeason()
                if (mediaSeason != null) {
                    emit(Resource.Success(mediaSeason))
                } else {
                    emit(Resource.Error("Failed to map season data"))
                }
            }
            is Resource.Error -> {
                emit(Resource.Error(response.message ?: "Failed to fetch season details"))
            }
            is Resource.Loading -> {}
        }
    }

    override fun discoverMedia(
        type: String,
        sortBy: String,
        genres: String?,
        yearStart: Int?,
        yearEnd: Int?,
        voteAverageGte: Float?,
        language: String?
    ): Flow<Resource<List<MediaSearchResult>>> = flow {
        emit(Resource.Loading())
        val response = if (type == "movie") {
            remoteDataSource.discoverTmdbMovie(
                sortBy = sortBy,
                genres = genres,
                yearStart = yearStart,
                yearEnd = yearEnd,
                voteAverageGte = voteAverageGte,
                language = language
            )
        } else {
            remoteDataSource.discoverTmdbTv(
                sortBy = sortBy,
                genres = genres,
                yearStart = yearStart,
                yearEnd = yearEnd,
                voteAverageGte = voteAverageGte,
                language = language
            )
        }

        when (response) {
            is Resource.Success -> {
                val results = response.data?.results?.map { it.toMediaSearchResult(fallbackMediaType = type) } ?: emptyList()
                emit(Resource.Success(results))
            }
            is Resource.Error -> {
                emit(Resource.Error(response.message ?: "Discovery Error"))
            }
            is Resource.Loading -> {}
        }
    }
}
