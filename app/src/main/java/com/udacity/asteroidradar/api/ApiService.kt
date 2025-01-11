package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov"
private const val KEY = "0kTKFfK8GHnt1lvSxnkRohhSXqK1m7mUgd2ZgtJe"

interface ApiService {

    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroidList(
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("api_key") key: String = KEY
    ): String

    @GET("/planetary/apod")
    suspend fun getPhoto(
        @Query("date") date: String? = null,
        @Query("start_date") startDate: String? = null,
        @Query("end_date") endDate: String? = null,
        @Query("count") count: Int? = null,
        @Query("thumbs") thumbs: Boolean? = null,
        @Query("api_key") key: String = KEY
    ): PictureOfDay

    @GET("/neo/rest/v1/neo")
    suspend fun getAsteroid(
        @Query("asteroid_id") asteroidId: Int,
        @Query("api_key") key: String = KEY
    ): Asteroid
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object AsteroidApi {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
