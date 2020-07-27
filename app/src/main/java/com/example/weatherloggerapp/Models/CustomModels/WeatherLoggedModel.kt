package com.example.weatherloggerapp.Models.CustomModels

import java.time.format.DateTimeFormatter

class WeatherLoggedModel {
   private var temp: String? = null
   private var date:String? =null

     fun getTemp(): String? {return temp}
    fun setTemp(temp:String){this.temp=temp}

    fun getCurrentDate(): String? {return date}
    fun setCurrentDate(date:String){this.date=date}
}