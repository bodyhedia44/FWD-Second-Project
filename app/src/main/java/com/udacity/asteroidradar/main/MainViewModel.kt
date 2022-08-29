package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ImageApi
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.log

class MainViewModel : ViewModel() {

    init {
        getimage()
    }


    private val _imageofday=MutableLiveData<PictureOfDay>()
    val imageofday:LiveData<PictureOfDay>
        get() = _imageofday


    private fun getimage(){
        ImageApi.retrofitService.getiamge(Constants.API_KEY).enqueue(object:Callback,
            retrofit2.Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
               _imageofday.value=response.body()
            }

            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                Log.d("erorrrr","erorrrrrrr")
            }

        } )
    }
}