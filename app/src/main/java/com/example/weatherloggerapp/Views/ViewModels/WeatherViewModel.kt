package com.example.weatherloggerapp.Views.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherloggerapp.Models.WeatherModel
import com.example.weatherloggerapp.Repositories.RoomDB.TempRoomDatabase
import com.example.weatherloggerapp.Repositories.RoomDB.WeatherTemp
import com.example.weatherloggerapp.Repositories.TempRepository
import com.example.weatherloggerapp.Repositories.WeatherRepository
import com.example.weatherloggerapp.Views.Utils.application
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel : ViewModel()
    {
        var weatherRepositry: WeatherRepository? = null
        var currentWeatherLiveData: MutableLiveData<WeatherModel>? = null
        var currentWeatherDate: MutableLiveData<String>? = null
        var longitude = 0.0
        var latitude = 0.0
        var disposable: Disposable? = null
        val repository: TempRepository
        val allTemps: LiveData<List<WeatherTemp>>

        init {
            weatherRepositry = WeatherRepository()
            currentWeatherLiveData = MutableLiveData()
            currentWeatherDate = MutableLiveData()
            val wordsDao = TempRoomDatabase.getDatabase(application.ctx!!,viewModelScope).tempDao()
            repository = TempRepository(wordsDao)
            allTemps = repository.allTemps
        }

        fun getCurrentWeather() {
            disposable=
            weatherRepositry!!.getCurrentWeather(latitude, longitude)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            response -> currentWeatherLiveData!!.setValue(response) }
                ) { error -> showError(error.toString()) }
        }

        fun showError(errorMsg: String?) {
            Log.d("Error Msg", errorMsg)
        }
        fun setLongLat(longt: Double, lat: Double) {
            longitude = longt
            latitude = lat
        }

        fun getWeatherLiveData(): LiveData<WeatherModel?>? {
            return currentWeatherLiveData
        }

        fun disposeCalling(){
            disposable?.dispose()
        }


        fun insertTempDB(temp: WeatherTemp) = viewModelScope.launch(Dispatchers.IO) {
            repository.insert(temp)
        }

}