package com.arisman.nangdia.data.remote

import com.arisman.nangdia.data.remote.dto.MdbListListResponse
import com.arisman.nangdia.data.remote.dto.MediaDetailResponse
import com.arisman.nangdia.data.remote.dto.SearchResponse
import com.arisman.nangdia.data.remote.dto.TmdbSearchResponse
import com.arisman.nangdia.presentation.detail.canonicalMediaType
import com.arisman.nangdia.util.Resource
import com.arisman.nangdia.BuildConfig
import javax.inject.Inject

class MdbListRemoteDataSource @Inject constructor(
    private val mdbApi: MdbListService,
    private val tmdbApi: TmdbService,
    private val imdbApi: ImdbApi
) {
    suspend fun searchMdb(
        query: String,
        page: Int,
        type: String? = null,
        year: String? = null
    ): Resource<SearchResponse> {
        return try {
            val response = mdbApi.search(query = query, page = page) // Note: MDBList any search doesn't support type/year directly in this endpoint
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun searchTmdbMovie(query: String, page: Int = 1, year: String? = null): Resource<TmdbSearchResponse> {
        return try {
            val response = tmdbApi.searchMovie(
                query = query,
                apiKey = BuildConfig.TMDB_API_KEY,
                page = page,
                year = year
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun searchTmdbTv(query: String, page: Int = 1, year: String? = null): Resource<TmdbSearchResponse> {
        return try {
            val response = tmdbApi.searchTv(
                query = query,
                apiKey = BuildConfig.TMDB_API_KEY,
                page = page,
                year = year
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun searchTmdb(query: String, page: Int = 1): Resource<TmdbSearchResponse> {
        return try {
            val response = tmdbApi.searchMulti(
                query = query,
                apiKey = BuildConfig.TMDB_API_KEY,
                page = page
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getMediaDetails(
        id: String,
        mediaType: String,
        provider: String
    ): Resource<MediaDetailResponse> {
        return try {
            val response = mdbApi.getMediaDetails(
                mediaProvider = provider,
                mediaType = canonicalMediaType(mediaType),
                mediaId = id
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getPopularMovies(page: Int = 1): Resource<TmdbSearchResponse> {
        return try {
            val response = tmdbApi.getPopularMovies(
                apiKey = BuildConfig.TMDB_API_KEY,
                page = page
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getPopularTvShows(page: Int = 1): Resource<TmdbSearchResponse> {
        return try {
            val response = tmdbApi.getPopularTvShows(
                apiKey = BuildConfig.TMDB_API_KEY,
                page = page
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getMdbListItems(username: String, slug: String): Resource<MdbListListResponse> {
        return try {
            val response = mdbApi.getListItems(username = username, slug = slug, limit = 20)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getTopLists(limit: Int = 20): Resource<List<com.arisman.nangdia.data.remote.dto.MdbListSummaryDto>> {
        return try {
            val response = mdbApi.getTopLists(limit = limit)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getListMetadata(username: String, slug: String): Resource<com.arisman.nangdia.data.remote.dto.MdbListSummaryDto> {
        return try {
            val response = mdbApi.getListMetadata(username = username, slug = slug)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun getParentsGuide(imdbId: String): Resource<com.arisman.nangdia.data.remote.dto.ImdbParentsGuideResponse> {
        return try {
            val response = imdbApi.getParentsGuide(titleId = imdbId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred fetching parental guide")
        }
    }

    suspend fun getTmdbDetail(tmdbId: Int, mediaType: String): Resource<com.arisman.nangdia.data.remote.dto.TmdbDetailResponse> {
        return try {
            val response = if (mediaType.lowercase() == "show" || mediaType.lowercase() == "tv")
                tmdbApi.getTvDetail(tvId = tmdbId, apiKey = BuildConfig.TMDB_API_KEY)
            else
                tmdbApi.getMovieDetail(movieId = tmdbId, apiKey = BuildConfig.TMDB_API_KEY)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred fetching TMDB detail")
        }
    }

    suspend fun getAwards(imdbId: String): Resource<com.arisman.nangdia.data.remote.dto.ImdbAwardsResponse> {
        return try {
            val response = imdbApi.getAwards(titleId = imdbId)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred fetching awards")
        }
    }

    suspend fun getPersonDetail(personId: Int): Resource<com.arisman.nangdia.data.remote.dto.TmdbPersonDetailResponse> {
        return try {
            val response = tmdbApi.getPerson(personId = personId, apiKey = BuildConfig.TMDB_API_KEY)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred fetching person detail")
        }
    }

    suspend fun getPersonCombinedCredits(personId: Int): Resource<com.arisman.nangdia.data.remote.dto.TmdbPersonCreditsResponse> {
        return try {
            val response = tmdbApi.getPersonCredits(personId = personId, apiKey = BuildConfig.TMDB_API_KEY)
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred fetching person credits")
        }
    }

    suspend fun getTvSeasonDetail(tvId: Int, seasonNumber: Int): Resource<com.arisman.nangdia.data.remote.dto.TvSeasonResponse> {
        return try {
            val response = tmdbApi.getTvSeasonDetail(
                tvId = tvId,
                seasonNumber = seasonNumber,
                apiKey = BuildConfig.TMDB_API_KEY
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred fetching season detail")
        }
    }

    suspend fun discoverTmdbMovie(
        sortBy: String = "popularity.desc",
        genres: String? = null,
        yearStart: Int? = null,
        yearEnd: Int? = null,
        voteAverageGte: Float? = null,
        language: String? = null
    ): Resource<TmdbSearchResponse> {
        return try {
            val response = tmdbApi.discoverMovie(
                apiKey = BuildConfig.TMDB_API_KEY,
                sortBy = sortBy,
                withGenres = genres,
                releaseDateGte = yearStart?.let { "$it-01-01" },
                releaseDateLte = yearEnd?.let { "$it-12-31" },
                voteAverageGte = voteAverageGte,
                withOriginalLanguage = language
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }

    suspend fun discoverTmdbTv(
        sortBy: String = "popularity.desc",
        genres: String? = null,
        yearStart: Int? = null,
        yearEnd: Int? = null,
        voteAverageGte: Float? = null,
        language: String? = null
    ): Resource<TmdbSearchResponse> {
        return try {
            val response = tmdbApi.discoverTv(
                apiKey = BuildConfig.TMDB_API_KEY,
                sortBy = sortBy,
                withGenres = genres,
                firstAirDateGte = yearStart?.let { "$it-01-01" },
                firstAirDateLte = yearEnd?.let { "$it-12-31" },
                voteAverageGte = voteAverageGte,
                withOriginalLanguage = language
            )
            Resource.Success(response)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An unknown error occurred")
        }
    }
}

