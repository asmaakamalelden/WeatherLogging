package com.example.weatherloggerapp.Views

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherloggerapp.Models.CustomModels.WeatherLoggedModel
import com.example.weatherloggerapp.R
import com.example.weatherloggerapp.Repositories.RoomDB.WeatherTemp
import com.example.weatherloggerapp.Views.ViewModels.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), LocationListener {
    protected var locationManager: LocationManager? = null
    var lat = 0.0
    var longt = 0.0
    var tempList = ArrayList<WeatherLoggedModel>()
    lateinit var viewModel: WeatherViewModel
    lateinit var adapter: WeatherAdapter


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        adapter = WeatherAdapter(this)
        rv_id.adapter = adapter
        rv_id.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProviders.of(this).get(WeatherViewModel::class.java)

        init()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun init() {
        getTempDB()
        getCurrentLocation()
        add_btn.setOnClickListener {
            viewModel.getCurrentWeather()
            getTempFromServer()
        }

    }

    fun getTempFromServer() {
        var temp: String = "295"
        viewModel.getWeatherLiveData()?.observe(this, Observer {
            temp = it?.current?.temp.toString()
        })
        var date = getCurrentDate()
        viewModel.insertTempDB(WeatherTemp(null, temp, date))
    }

    fun getCurrentDate(): String {
        val formatedDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val formatedTime = SimpleDateFormat("HH:mm").format(Date())
        val DateTime = "$formatedDate  $formatedTime"
        return DateTime
    }

    fun getCurrentLocation() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            1
        )
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val location =
            locationManager!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        location?.let { onLocationChanged(it) }
            ?: locationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                0f,
                this
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTempDB() {
        viewModel.allTemps?.observe(this, Observer {
            adapter.setTemps(it)
        })
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            lat = location.getLatitude()
            longt = location.getLongitude()
            viewModel.setLongLat(longt, lat)
            Log.d("Latitude", lat.toString() + "" + longt)
        }

    }

    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
        Log.d("Latitude", "status")
    }

    override fun onProviderEnabled(p0: String?) {
        Log.d("Latitude", "enable")
    }

    override fun onProviderDisabled(p0: String?) {
        Log.d("Latitude", "disable")
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.disposable
    }
}