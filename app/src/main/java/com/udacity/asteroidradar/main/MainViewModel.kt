package com.udacity.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ImageApi
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.log

class MainViewModel : ViewModel() {

    private val _imageofday=MutableLiveData<String>()

    val imageofday:LiveData<String>
        get() = _imageofday


    private fun getimage(){
        ImageApi.retrofitService.getiamge().enqueue(object:Callback,
            retrofit2.Callback<PictureOfDay> {
            override fun onResponse(call: Call<PictureOfDay>, response: Response<PictureOfDay>) {
                Log.d("done",response.body().toString())
            }

            override fun onFailure(call: Call<PictureOfDay>, t: Throwable) {
                Log.d("erorrrr","erorrrrrrr")
            }

        } )
    }
}