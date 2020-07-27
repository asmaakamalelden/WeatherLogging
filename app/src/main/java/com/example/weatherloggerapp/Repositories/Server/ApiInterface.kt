package com.example.weatherloggerapp.Repositories.Server

import com.example.weatherloggerapp.Models.WeatherModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/data/2.5/onecall")
    fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Observable<WeatherModel>
}