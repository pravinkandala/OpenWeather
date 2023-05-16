package com.example.weather.utils

object WeatherUtils {
    fun convertKelvinToCelsius(temperature: Double): String {
        val celsius = temperature - 273.15
        val formattedTemperature = String.format("%.2f", celsius)
        return "$formattedTemperature °C"
    }

    fun convertKelvinToFahrenheit(temperature: Double): String {
        val fahrenheit = (temperature - 273.15) * 9 / 5 + 32
        val formattedTemperature = String.format("%.2f", fahrenheit)
        return "$formattedTemperature °F"
    }
}