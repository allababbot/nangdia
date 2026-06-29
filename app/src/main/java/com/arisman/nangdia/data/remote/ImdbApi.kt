package com.arisman.nangdia.data.remote

import com.arisman.nangdia.data.remote.dto.ImdbParentsGuideResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ImdbApi {
    @Headers("Accept: application/json")
    @GET("titles/{titleId}/parentsGuide")
    suspend fun getParentsGuide(
        @Path("titleId") titleId: String
    ): ImdbParentsGuideResponse
    
    @Headers("Accept: application/json")
    @GET("titles/{titleId}/awardNominations")
    suspend fun getAwards(
        @Path("titleId") titleId: String
    ): com.arisman.nangdia.data.remote.dto.ImdbAwardsResponse
    
    companion object {
        const val BASE_URL = "https://api.imdbapi.dev/"
    }
}

