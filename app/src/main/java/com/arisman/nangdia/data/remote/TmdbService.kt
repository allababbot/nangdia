package com.arisman.nangdia.data.remote

import com.arisman.nangdia.data.remote.dto.TmdbSearchResponse
import com.arisman.nangdia.data.remote.dto.TmdbDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbService {

    @GET("search/multi")
    suspend fun searchMulti(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("include_adult") includeAdult: Boolean = false
    ): TmdbSearchResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("primary_release_year") year: String? = null
    ): TmdbSearchResponse

    @GET("search/tv")
    suspend fun searchTv(
        @Query("query") query: String,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("first_air_date_year") year: String? = null
    ): TmdbSearchResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TmdbSearchResponse

    @GET("tv/popular")
    suspend fun getPopularTvShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TmdbSearchResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") appendToResponse: String = "release_dates,recommendations,credits"
    ): TmdbDetailResponse

    @GET("tv/{tv_id}")
    suspend fun getTvDetail(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("append_to_response") appendToResponse: String = "content_ratings,recommendations,credits"
    ): TmdbDetailResponse

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getTvSeasonDetail(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): com.arisman.nangdia.data.remote.dto.TvSeasonResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): com.arisman.nangdia.data.remote.dto.TmdbCreditsResponse

    @GET("tv/{tv_id}/credits")
    suspend fun getTvCredits(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ): com.arisman.nangdia.data.remote.dto.TmdbCreditsResponse

    @GET("person/{person_id}")
    suspend fun getPerson(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): com.arisman.nangdia.data.remote.dto.TmdbPersonDetailResponse

    @GET("person/{person_id}/combined_credits")
    suspend fun getPersonCredits(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): com.arisman.nangdia.data.remote.dto.TmdbPersonCreditsResponse

    @GET("discover/movie")
    suspend fun discoverMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") withGenres: String? = null,
        @Query("primary_release_date.gte") releaseDateGte: String? = null,
        @Query("primary_release_date.lte") releaseDateLte: String? = null,
        @Query("vote_average.gte") voteAverageGte: Float? = null,
        @Query("vote_average.lte") voteAverageLte: Float? = null,
        @Query("with_keywords") withKeywords: String? = null,
        @Query("with_original_language") withOriginalLanguage: String? = null
    ): TmdbSearchResponse

    @GET("discover/tv")
    suspend fun discoverTv(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("with_genres") withGenres: String? = null,
        @Query("first_air_date.gte") firstAirDateGte: String? = null,
        @Query("first_air_date.lte") firstAirDateLte: String? = null,
        @Query("vote_average.gte") voteAverageGte: Float? = null,
        @Query("vote_average.lte") voteAverageLte: Float? = null,
        @Query("with_keywords") withKeywords: String? = null,
        @Query("with_original_language") withOriginalLanguage: String? = null
    ): TmdbSearchResponse

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}
