package com.example.weather.network

import com.example.weather.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    //TODO: Place API_KEY in secure place and pass it here
    @GET("weather")
    suspend fun getWeatherData(
        @Query("q") location: String,
        @Query("appid") apiKey: String = "API_KEY"
    ): Response<WeatherResponse>
}