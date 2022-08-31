package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.log

class MainViewModel : ViewModel() {


    private val _imageofday=MutableLiveData<PictureOfDay>()
    val imageofday:LiveData<PictureOfDay>
        get() = _imageofday

    private val _weel=MutableLiveData<Boolean>()
    val weel:LiveData<Boolean>
        get() = _weel


    private val _asteroids=MutableLiveData<List<Asteroid>>()
    val asteroids:LiveData<List<Asteroid>>
        get() = _asteroids
init {
    getAsteroids()
    _weel.value=false
}

     fun getimage(){
        Api.retrofitService.getiamge(mapOf(
            "api_key" to Constants.API_KEY
        )).enqueue(object:Callback,
            retrofit2.Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
               _imageofday.value=response.body()
            }

            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                Log.d("erorrrr","erorrrrrrr")
            }

        } )
    }

    fun getAsteroids(){
        AsteroidApi.retrofitService.getasteroids(mapOf(
            "start_date" to "2022-08-31",
            "api_key" to Constants.API_KEY
        )).enqueue(object:Callback,
            retrofit2.Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
               val  jsonObj =  JSONObject(response.body()!!.string())
                val res= parseAsteroidsJsonResult(jsonObj)
                Log.d("data",res.size.toString())
                if (res.size==0) _weel.value=false
                else {
                    _weel.value=true
                    _asteroids.value=res
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("erorrrr",t.message.toString())
            }


        } )
    }
}