package com.example.weather.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.repositories.WeatherRepository
import com.example.weather.data.model.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherResponse?>()
    val weatherData: LiveData<WeatherResponse?> = _weatherData

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        viewModelScope.launch {
            val lastFetchedWeatherData = weatherRepository.getLastFetchedWeatherData()
            _weatherData.value = lastFetchedWeatherData?.weatherData
        }
    }

    fun getWeatherData(location: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = weatherRepository.getWeatherData(location)
                if (response.isSuccessful) {
                    val weatherResponse = response.body()
                    _weatherData.postValue(weatherResponse)
                } else {
                    _error.postValue(response.message())
                }
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }
}


