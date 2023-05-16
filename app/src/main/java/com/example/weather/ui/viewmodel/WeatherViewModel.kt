package com.example.weather.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherRepository
import com.example.weather.data.WeatherResponse
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    val weatherData = MutableLiveData<WeatherResponse>()
    val error = MutableLiveData<String>()

    fun getWeatherData(location: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = weatherRepository.getWeatherData(location, apiKey)
                if (response.isSuccessful) {
                    weatherData.value = response.body()
                } else {
                    error.value = "Failed to fetch weather data"
                }
            } catch (e: Exception) {
                error.value = "An error occurred"
            }
        }
    }
}