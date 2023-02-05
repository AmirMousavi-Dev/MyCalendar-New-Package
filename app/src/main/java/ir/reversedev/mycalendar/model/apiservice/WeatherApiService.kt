package ir.reversedev.mycalendar.model.apiservice

import ir.reversedev.mycalendar.model.data.ForecastEntity
import ir.reversedev.mycalendar.model.data.WeatherEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query(value = "lat") lat: Double,
        @Query(value = "lon") lon: Double,
        @Query(value = "appid") appid: String = "a3a5f42654745aacdd7a4d013a4c8334",
        @Query(value = "units") units :String = "metric",
        @Query(value = "lang") lang: String = "fa",

        ): WeatherEntity

    @GET("forecast")
    suspend fun getForecast (
        @Query(value = "lat") lat: Double,
        @Query(value = "lon") lon: Double,
        @Query(value = "appid") appid: String = "a3a5f42654745aacdd7a4d013a4c8334",
        @Query(value = "units") units :String = "metric",
        @Query(value = "lang") lang: String = "fa",
    ) : ForecastEntity
}