package com.example.weather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.weather.data.WeatherResponse
import com.example.weather.ui.viewmodel.WeatherViewModel
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

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WeatherApp() {
        val weatherData by weatherViewModel.weatherData.observeAsState()
        val error by weatherViewModel.error.observeAsState()

        Scaffold(
            topBar = { WeatherAppBar() },
            content = { padding ->
                Column(modifier = Modifier.padding(padding)) {
                        WeatherContent(weatherData, error)
                }
            }
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WeatherAppBar() {
        TopAppBar(
            title = { Text(text = "Weather App") }
        )
    }

    @Composable
    fun WeatherContent(weatherData: WeatherResponse?, error: String?) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherSearch()

            Spacer(modifier = Modifier.height(16.dp))

            if (weatherData != null) {
                WeatherDetails(weatherData)
            } else if (error != null) {
                Text(text = "Error: $error")
            }
        }
    }

    @Composable
    fun WeatherSearch() {
        var location by remember { mutableStateOf("") }

        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text(text = "Location") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val apiKey = "cc27220c8cff89afc2d205dba40b5c29"
                weatherViewModel.getWeatherData(location, apiKey)
            }
        ) {
            Text(text = "Search")
        }
    }

    @Composable
    fun WeatherDetails(weatherData: WeatherResponse) {
        Column {
            Text(text = "Location: ${weatherData.name}")

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Temperature: ${weatherData.main.temp} K")

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Description: ${weatherData.weather[0].description}")
        }
    }
}