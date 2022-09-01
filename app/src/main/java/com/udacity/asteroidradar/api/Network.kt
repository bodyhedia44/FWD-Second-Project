package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.BASE_URL)
    .build()

private val retrofit2 = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface ImageData{
    @GET(value = "planetary/apod")
    fun getiamge(@QueryMap options:Map<String,String>):
            Call<PictureOfDay>
}
interface AsteroidData{
    @GET("neo/rest/v1/feed")
    fun getasteroids(@QueryMap options:Map<String,String> ):
            Call<ResponseBody>
}


object Api {
    val retrofitService : ImageData by lazy {
        retrofit.create(ImageData::class.java)
    }
}
object AsteroidApi {
    val retrofitService : AsteroidData by lazy {
        retrofit2.create(AsteroidData::class.java)
    }
}
