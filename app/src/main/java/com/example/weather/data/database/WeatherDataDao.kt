package com.example.weather.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeatherDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weatherData: WeatherDataEntity)

    @Query("SELECT * FROM weather_data ORDER BY id DESC LIMIT 1")
    suspend fun getLastFetchedWeatherData(): WeatherDataEntity?
}
