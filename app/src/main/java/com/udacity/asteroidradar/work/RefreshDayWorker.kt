package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Repo.Repo
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database=getInstance(applicationContext)
        val repo =  Repo(database)


        try {
            repo.refresh()
            return Result.success()
        }catch (e:HttpException){
            return Result.retry()
        }

    }
}