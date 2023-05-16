package com.example.weather.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.example.weather.data.model.WeatherResponse
import com.example.weather.ui.viewmodel.WeatherViewModel
import com.example.weather.utils.WeatherUtils

@Composable
fun WeatherDetails(
    weatherViewModel: WeatherViewModel,
    weatherData: WeatherResponse,
    imageLoader: ImageLoader,
    weatherUtils: WeatherUtils
) {
    val lastLocation: String? = weatherViewModel.weatherData.value?.name

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        lastLocation?.let { location ->
            Text(text = "Last Fetched Location: $location")
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(text = "Location: ${weatherData.name}")
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Temperature: ${weatherUtils.convertKelvinToFahrenheit(weatherData.main.temp)}")
        Spacer(modifier = Modifier.height(8.dp))

        weatherData.weather.forEach { weather ->
            val weatherDescription = weather.description
            val weatherIconUrl =
                "https://openweathermap.org/img/wn/${weather.icon}.png"

            Text(text = weatherDescription)
            AsyncImage(
                model = weatherIconUrl,
                contentDescription = weatherDescription,
                imageLoader = imageLoader,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )
        }
    }
}