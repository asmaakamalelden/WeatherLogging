package com.example.weatherloggerapp.Repositories

import com.example.weatherloggerapp.Models.WeatherModel
import com.example.weatherloggerapp.Repositories.Server.ApiClient
import com.example.weatherloggerapp.Repositories.Server.ApiInterface
import io.reactivex.Observable

class WeatherRepository {
    private val apiInterface: ApiInterface = ApiClient.getInstance()?.getApi!!


    fun getCurrentWeather(
        lat: Double,
        lon: Double
    ): Observable<WeatherModel> {
        return apiInterface.getCurrentWeather(lat, lon)
    }
}