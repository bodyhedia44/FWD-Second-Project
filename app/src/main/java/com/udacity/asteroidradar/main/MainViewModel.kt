package com.udacity.asteroidradar.main

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.Repo.Repo
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.Api
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.security.auth.callback.Callback
import kotlin.math.log

class MainViewModel(application: Application) : ViewModel() {


    private val _imageofday=MutableLiveData<PictureOfDay>()
    val imageofday:LiveData<PictureOfDay>
        get() = _imageofday

    private val _weel=MutableLiveData<Boolean>()
    val weel:LiveData<Boolean>
        get() = _weel


    private val database=getInstance(application)
    private val viewmodeljob= SupervisorJob()
    private val repo =Repo(database)
    private val viewmodelscope= CoroutineScope(viewmodeljob+Dispatchers.Main)



init {
    _weel.value=false
    viewmodelscope.launch {
        if(isOnline(application.applicationContext)) {
            repo.refresh()
            getimage()
        }else{
            _imageofday.value=PictureOfDay(mediaType = "", title = "space image", url = "no")
        }
    }
}

    override fun onCleared() {
        super.onCleared()
        viewmodeljob.cancel()
    }
    var aste=repo.AllData
    val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT))
    } else { TODO("VERSION.SDK_INT < O") }


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

    fun changeweel(){
        if (aste.value!!.size==0)_weel.value=false
        else _weel.value=true
    }

      fun getAll(): List<Asteroid>{
          return aste.value!!
    }

     fun getToday(): List<Asteroid> {
         var temp = listOf<Asteroid>()
         for (i in aste.value!!){
            if (i.closeApproachDate==current)temp+=i
         }
         return temp
     }


    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}
