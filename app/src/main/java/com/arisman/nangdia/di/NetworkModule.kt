package com.arisman.nangdia.di

import com.arisman.nangdia.BuildConfig
import com.arisman.nangdia.data.remote.MdbListService
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.logging.HttpLoggingInterceptor
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton
import javax.inject.Named
import android.content.Context
import com.arisman.nangdia.data.remote.TmdbService

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val originalUrl = originalRequest.url
                
                // Only add API key to requests going to the API endpoint, not for images
                val request = if (originalUrl.host == "api.mdblist.com") {
                    val url = originalUrl.newBuilder()
                        .addQueryParameter("apikey", BuildConfig.MDB_LIST_API_KEY)
                        .build()
                    originalRequest.newBuilder().url(url).build()
                } else if (originalUrl.host.contains("tmdb.org")) {
                    // Avoid passing MDBList API key to TMDB
                    originalRequest
                } else {
                    originalRequest
                }
                
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Provides
    @Singleton
    fun provideMdbListRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(MdbListService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    @Named("TmdbRetrofit")
    fun provideTmdbRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(TmdbService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideMdbListService(retrofit: Retrofit): MdbListService {
        return retrofit.create(MdbListService::class.java)
    }

    @Provides
    @Singleton
    fun provideTmdbService(@Named("TmdbRetrofit") retrofit: Retrofit): TmdbService {
        return retrofit.create(TmdbService::class.java)
    }

    @Provides
    @Singleton
    @Named("ImdbRetrofit")
    fun provideImdbRetrofit(okHttpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(com.arisman.nangdia.data.remote.ImdbApi.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    @Provides
    @Singleton
    fun provideImdbService(@Named("ImdbRetrofit") retrofit: Retrofit): com.arisman.nangdia.data.remote.ImdbApi {
        return retrofit.create(com.arisman.nangdia.data.remote.ImdbApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .crossfade(true)
            .build()
    }
}

