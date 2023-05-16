package com.example.weather.ui

import android.content.Context
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.weather.data.WeatherResponse
import com.example.weather.ui.viewmodel.WeatherViewModel
import com.example.weather.utils.convertKelvinToFahrenheit
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val weatherViewModel: WeatherViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherApp(
                context = baseContext
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WeatherApp(context: Context) {
        val weatherData by weatherViewModel.weatherData.observeAsState()
        val error by weatherViewModel.error.observeAsState()

        Scaffold(
            topBar = { WeatherAppBar() },
            content = { padding ->
                Column(modifier = Modifier.padding(padding)) {
                        WeatherContent(context, weatherData, error)
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
    fun WeatherContent(context: Context, weatherData: WeatherResponse?, error: String?) {
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
                WeatherDetails(context, weatherData)
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
    fun WeatherDetails(context: Context, weatherData: WeatherResponse) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Location: ${weatherData.name}")

            Spacer(modifier = Modifier.height(8.dp))

            val temperatureInKelvin = weatherData.main.temp
            val temperatureInFahrenheit = convertKelvinToFahrenheit(temperatureInKelvin)

            Text(text = "Temperature: ${temperatureInFahrenheit.roundToInt()} °F")

            Spacer(modifier = Modifier.height(8.dp))

            weatherData.weather.forEach { weather ->
                val weatherDescription = weather.description
                val weatherIconUrl = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"

                Text(text = weatherDescription.uppercase(Locale.getDefault()), modifier = Modifier.padding(bottom = 16.dp))

                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(weatherIconUrl)
                        .build(),
                    contentDescription = weather.description,
                    modifier = Modifier.size(64.dp)
                )
            }
        }
    }
}