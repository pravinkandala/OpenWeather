package com.example.weather.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.data.model.WeatherResponse

@Entity(tableName = "weather_data")
data class WeatherDataEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val location: String,
    val weatherData: WeatherResponse
)