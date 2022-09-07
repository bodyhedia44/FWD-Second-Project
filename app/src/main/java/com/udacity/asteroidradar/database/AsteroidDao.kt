package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(asteroid: List<Asteroid>)

    @Update
    suspend fun update(asteroid: Asteroid)


    @Query("SELECT * FROM asteroid_table ORDER BY closeApproachDate DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>

    @Query("SELECT * FROM asteroid_table Where closeApproachDate == date('now','localtime')")
    fun getTodayAsteroids(): LiveData<List<Asteroid>>

    @Query("Delete FROM asteroid_table Where closeApproachDate < date('now')")
    suspend fun deleteALlOld()
}