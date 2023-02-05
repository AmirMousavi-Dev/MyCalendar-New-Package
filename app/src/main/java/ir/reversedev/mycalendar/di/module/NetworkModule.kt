package ir.reversedev.mycalendar.di.module

import dagger.Module
import dagger.Provides
import ir.reversedev.mycalendar.model.apiservice.MonasebatApiService
import ir.reversedev.mycalendar.model.apiservice.OghatApiService
import ir.reversedev.mycalendar.model.apiservice.WeatherApiService
import ir.reversedev.mycalendar.util.Constant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named("weatherRetrofit")
    fun provideWeatherRetrofit ( client: OkHttpClient) : Retrofit {
        return Retrofit.Builder().baseUrl(Constant.WEATHER_BASE_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideWeatherApiService (@Named("weatherRetrofit")retrofit: Retrofit) :WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }

    @Singleton
    @Provides
    @Named("keybitRetrofit")
    fun provideOghatRetrofit( client: OkHttpClient) : Retrofit {
        return Retrofit.Builder().baseUrl(Constant.KEYBIT_BASE_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    @Named("monasebatRetrofit")
    fun provideMonasebatRetrofit( client: OkHttpClient) : Retrofit {
        return Retrofit.Builder().baseUrl(Constant.CODE_BAZAN_URL).client(client).addConverterFactory(
            GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun provideMonasebatApiService (@Named("monasebatRetrofit")retrofit: Retrofit) :MonasebatApiService {
        return  retrofit.create(MonasebatApiService::class.java)
    }
    @Singleton
    @Provides
    fun provideOghatApiService (@Named("keybitRetrofit")retrofit: Retrofit) :OghatApiService {
        return  retrofit.create(OghatApiService::class.java)
    }

}