package com.example.weather.di

import com.example.weather.data.WeatherRepository
import com.example.weather.network.WeatherService
import com.example.weather.ui.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
    }

    single { WeatherRepository(get()) }

    viewModel { WeatherViewModel(get()) }
}