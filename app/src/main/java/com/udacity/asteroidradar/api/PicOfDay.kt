package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()


interface ImaeOFDay{
    @GET(value = "planetary/apod")
    fun getiamge():
            Call<PictureOfDay>
}
object ImageApi {
    val retrofitService : ImaeOFDay by lazy {
        retrofit.create(ImaeOFDay::class.java)
    }
}
