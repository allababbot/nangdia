package com.arisman.nangdia.domain.repository

import com.arisman.nangdia.domain.model.MediaDetail
import com.arisman.nangdia.domain.model.MediaSearchResult
import com.arisman.nangdia.util.Resource
import kotlinx.coroutines.flow.Flow

interface MdbListRepository {
    fun searchMedia(
        query: String,
        type: String? = null,
        year: String? = null
    ): Flow<Resource<List<MediaSearchResult>>>

    fun getMediaDetail(
        id: String,
        mediaType: String,
        provider: String
    ): Flow<Resource<MediaDetail>>

    fun getPopularMovies(): Flow<Resource<List<MediaSearchResult>>>
    fun getPopularTvShows(): Flow<Resource<List<MediaSearchResult>>>

    fun getDiscoverLists(): Flow<Resource<List<com.arisman.nangdia.domain.model.MdbListMetadata>>>

    fun getMdbListMetadata(username: String, slug: String): Flow<Resource<com.arisman.nangdia.domain.model.MdbListMetadata>>

    fun getMdbList(username: String, slug: String): Flow<Resource<List<MediaSearchResult>>>

    fun getPersonDetail(personId: Int): Flow<Resource<com.arisman.nangdia.domain.model.PersonDetail>>

    fun getTvSeasonDetail(tvId: Int, seasonNumber: Int): Flow<Resource<com.arisman.nangdia.domain.model.MediaSeason>>

    fun discoverMedia(
        type: String, // "movie" or "tv"
        sortBy: String = "popularity.desc",
        genres: String? = null,
        yearStart: Int? = null,
        yearEnd: Int? = null,
        voteAverageGte: Float? = null,
        language: String? = null
    ): Flow<Resource<List<MediaSearchResult>>>
}
