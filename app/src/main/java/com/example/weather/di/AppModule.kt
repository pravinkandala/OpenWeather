package com.example.weather.di

import android.content.Context
import androidx.room.Room
import com.example.weather.data.database.WeatherDatabase
import com.example.weather.data.repositories.WeatherRepository
import com.example.weather.network.WeatherService
import com.example.weather.ui.viewmodel.WeatherViewModel
import com.example.weather.utils.CoilImageLoader
import com.example.weather.utils.WeatherUtils
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    // Retrofit
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(WeatherService::class.java)
    }

    // OkHttpClient
    single {
        val cacheSize = (10 * 1024 * 1024).toLong() // 10MB
        val cacheDirectory = get<Context>().cacheDir
        val cache = Cache(cacheDirectory, cacheSize)

        OkHttpClient.Builder()
            .cache(cache)
            .build()
    }

    // ImageLoader
    single {
        CoilImageLoader.create(get())
    }

    // Room Database
    single {
        Room.databaseBuilder(get(), WeatherDatabase::class.java, "weather_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<WeatherDatabase>().weatherDataDao() }

    single { WeatherRepository(
        weatherService = get(),
        weatherDao = get()
    ) }

    // ViewModel
    viewModel { WeatherViewModel(get()) }

    // Utils
    single { WeatherUtils }
}
