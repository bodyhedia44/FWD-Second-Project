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


    @Query("SELECT * FROM asteroid_table ORDER BY id DESC")
    fun getAllAsteroids(): LiveData<List<Asteroid>>
}