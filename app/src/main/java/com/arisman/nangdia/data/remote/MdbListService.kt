package com.arisman.nangdia.data.remote

import com.arisman.nangdia.data.remote.dto.MdbListListResponse
import com.arisman.nangdia.data.remote.dto.MdbListSummaryDto
import com.arisman.nangdia.data.remote.dto.MediaDetailResponse
import com.arisman.nangdia.data.remote.dto.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MdbListService {

    @GET("search/any")
    suspend fun search(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("append_to_response") appendToResponse: String = "poster"
    ): SearchResponse

    @GET("{media_provider}/{media_type}/{media_id}")
    suspend fun getMediaDetails(
        @Path("media_provider") mediaProvider: String,
        @Path("media_type") mediaType: String,
        @Path("media_id") mediaId: String
    ): MediaDetailResponse

    @GET("lists/{username}/{slug}/items")
    suspend fun getListItems(
        @Path("username") username: String,
        @Path("slug") slug: String,
        @Query("limit") limit: Int = 20,
        @Query("append_to_response") appendToResponse: String = "poster,ratings"
    ): MdbListListResponse

    @GET("lists/top")
    suspend fun getTopLists(
        @Query("limit") limit: Int = 20
    ): List<MdbListSummaryDto>

    @GET("lists/{username}/{slug}")
    suspend fun getListMetadata(
        @Path("username") username: String,
        @Path("slug") slug: String
    ): MdbListSummaryDto

    companion object {
        const val BASE_URL = "https://api.mdblist.com/"
    }
}

