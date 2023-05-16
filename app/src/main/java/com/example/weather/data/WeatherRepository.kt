package com.example.weather.data

import com.example.weather.network.WeatherService
import retrofit2.Response

class WeatherRepository(private val weatherService: WeatherService) {
    suspend fun getWeatherData(location: String, apiKey: String): Response<WeatherResponse> {
        return weatherService.getWeatherData(location = location, apiKey = apiKey)
    }
}