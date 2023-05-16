package com.example.weather.network

import com.example.weather.data.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") location: String,
        @Query("appid") apiKey: String
    ): Response<WeatherResponse>
}