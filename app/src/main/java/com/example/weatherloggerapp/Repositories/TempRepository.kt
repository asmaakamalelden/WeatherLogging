package com.example.weatherloggerapp.Repositories

import androidx.lifecycle.LiveData
import com.example.weatherloggerapp.Repositories.RoomDB.TempDao
import com.example.weatherloggerapp.Repositories.RoomDB.WeatherTemp


class TempRepository(private val tempDao: TempDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allTemps: LiveData<List<WeatherTemp>> = tempDao.getAllTemps()

    suspend fun insert(temp: WeatherTemp) {
        tempDao.insert(temp)
    }
}