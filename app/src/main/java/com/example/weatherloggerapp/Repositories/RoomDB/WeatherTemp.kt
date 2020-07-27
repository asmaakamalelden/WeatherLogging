package com.example.weatherloggerapp.Repositories.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "temp_table")
class WeatherTemp(@PrimaryKey(autoGenerate = true) var id:Long?=null,
                  @ColumnInfo(name = "temp") val temp: String
                  , @ColumnInfo(name = "currentdate") var currentdate: String
)