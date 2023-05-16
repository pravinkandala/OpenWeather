package com.example.weather.data.repositories

import com.example.weather.data.database.WeatherDataDao
import com.example.weather.data.database.WeatherDataEntity
import com.example.weather.data.model.WeatherResponse
import com.example.weather.network.WeatherService
import retrofit2.Response

class WeatherRepository(
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDataDao
) {
    suspend fun getWeatherData(location: String): Response<WeatherResponse> {
        val response = weatherService.getWeatherData(location = location)

        if (response.isSuccessful) {
            val weatherResponse = response.body()
            weatherResponse?.let {
                val weatherData = WeatherDataEntity(location = it.name, weatherData = it)
                weatherDao.insert(weatherData)
            }
        }

        return response
    }

    suspend fun getLastFetchedWeatherData(): WeatherDataEntity? {
        return weatherDao.getLastFetchedWeatherData()
    }
}




