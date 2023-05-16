package com.example.weather.data.database

import androidx.room.TypeConverter
import com.example.weather.data.model.WeatherResponse
import com.google.gson.Gson

class WeatherResponseConverter {
    @TypeConverter
    fun fromJson(json: String): WeatherResponse {
        return Gson().fromJson(json, WeatherResponse::class.java)
    }

    @TypeConverter
    fun toJson(weatherResponse: WeatherResponse): String {
        return Gson().toJson(weatherResponse)
    }
}