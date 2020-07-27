package com.example.weatherloggerapp.Repositories.RoomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TempDao {

    @Query("SELECT * from temp_table ")
    fun getAllTemps(): LiveData<List<WeatherTemp>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(temp: WeatherTemp)

    @Query("DELETE FROM temp_table")
    suspend fun deleteAll()
}