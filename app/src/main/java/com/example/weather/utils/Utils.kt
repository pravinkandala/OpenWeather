package com.example.weather.utils

fun convertKelvinToFahrenheit(temperatureInKelvin: Double): Double {
    return (temperatureInKelvin - 273.15) * 9 / 5 + 32
}