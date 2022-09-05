package com.udacity.asteroidradar.Repo

import android.os.Build
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.awaitResponse
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Repo(private val database:AsteroidDatabase) {
    
    
    val data=database.asteroidDatabaseDao.getAllAsteroids()

    suspend fun refresh(){
        val current = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.API_QUERY_DATE_FORMAT))
        } else { TODO("VERSION.SDK_INT < O") }

        withContext(Dispatchers.IO){
            database.asteroidDatabaseDao.deleteALlOld()
           val data =  AsteroidApi.retrofitService.getasteroids(
              mapOf(
                  "start_date" to current,
                  "api_key" to Constants.API_KEY
              )
          ).awaitResponse()
           val jsonObj = JSONObject(data.body()!!.string())
           val res = parseAsteroidsJsonResult(jsonObj)
           database.asteroidDatabaseDao.insert(res)
       }

    }
}


