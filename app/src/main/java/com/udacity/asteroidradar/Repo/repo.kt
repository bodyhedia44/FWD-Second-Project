package com.udacity.asteroidradar.Repo

import android.util.Log
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.await
import retrofit2.awaitResponse
import javax.security.auth.callback.Callback

class Repo(private val database:AsteroidDatabase) {
    
    
    val data=database.asteroidDatabaseDao.getAllAsteroids()

    suspend fun refresh(){

       withContext(Dispatchers.IO){
          val data =  AsteroidApi.retrofitService.getasteroids(
              mapOf(
                  "start_date" to "2022-09-4",
                  "api_key" to Constants.API_KEY
              )
          ).awaitResponse()
           val jsonObj = JSONObject(data.body()!!.string())
           val res = parseAsteroidsJsonResult(jsonObj)
           Log.d("data", res.toString())
           database.asteroidDatabaseDao.insert(res)
       }

    }
}


