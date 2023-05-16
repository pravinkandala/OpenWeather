package com.example.weather.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import com.example.weather.data.model.WeatherResponse
import com.example.weather.ui.components.WeatherDetails
import com.example.weather.ui.components.WeatherSearch
import com.example.weather.ui.viewmodel.WeatherViewModel
import com.example.weather.utils.WeatherUtils
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp()
        }
    }

    @Composable
    fun WeatherApp() {
        val weatherData by weatherViewModel.weatherData.observeAsState()
        val error by weatherViewModel.error.observeAsState()

        val imageLoader: ImageLoader = get()

        Scaffold(
            topBar = { WeatherAppBar() },
            content = { paddingValues ->
                WeatherContent(
                    modifier = Modifier.padding(paddingValues),
                    weatherData = weatherData,
                    error = error,
                    imageLoader = imageLoader
                )
            }
        )
    }

    @Composable
    fun WeatherAppBar() {
        TopAppBar(
            title = { Text(text = "Weather App") }
        )
    }

    @Composable
    fun WeatherContent(
        modifier: Modifier = Modifier,
        weatherData: WeatherResponse?,
        error: String?,
        imageLoader: ImageLoader
    ) {
        val weatherUtils: WeatherUtils by inject()

        Column(modifier = modifier) {
            WeatherSearch(
                onSearchClicked = { location ->
                    weatherViewModel.getWeatherData(location)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (weatherData != null) {
                WeatherDetails(
                    weatherData = weatherData,
                    imageLoader = imageLoader,
                    weatherUtils = weatherUtils,
                    weatherViewModel = weatherViewModel
                )
            } else if (error != null) {
                Text(text = "Error: $error")
            }
        }
    }
}