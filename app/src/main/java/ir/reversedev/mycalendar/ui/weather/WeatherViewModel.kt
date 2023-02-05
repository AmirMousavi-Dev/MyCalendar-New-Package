package ir.reversedev.mycalendar.ui.weather

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aminography.primecalendar.common.toPersian
import ir.reversedev.mycalendar.model.data.FavoriteEntity
import ir.reversedev.mycalendar.model.data.ForecastEntity
import ir.reversedev.mycalendar.model.data.WeatherEntity
import ir.reversedev.mycalendar.util.Event
import ir.reversedev.mycalendar.viewmodel.MainRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class WeatherViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {
    private val _favorite = MutableLiveData<Event<FavoriteEntity>>()
    val favorite: LiveData<Event<FavoriteEntity>>
        get() = _favorite
    private val _weather = MutableLiveData<Event<WeatherEntity>>()
    val weather: LiveData<Event<WeatherEntity>>
        get() = _weather
    private val _forecast = MutableLiveData<Event<ForecastEntity>>()
    val forecast: LiveData<Event<ForecastEntity>>
        get() = _forecast

    fun getWeather() {
        viewModelScope.launch {
            try {


                val favorite = mainRepository.getFavorite()
                _favorite.value = Event(favorite)
                async {
                    val dataWeather = mainRepository.getWeather(favorite.lat, favorite.lon)
                    _weather.value = Event(dataWeather)
                }
                async {
                    val dataForecast = mainRepository.getForecast(favorite.lat, favorite.lon)
                    _forecast.value = Event(dataForecast)
                }
            } catch (e : Exception) {
                getWeather()
                Log.v("test" , e.message?:"null")
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getTime(): String {
        val currentTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH")
        return formatter.format(currentTime)
    }

    fun toDay () :String{
        val calendar = Calendar.getInstance()
        val persian = calendar.toPersian()
        val data = persian.weekDayName
        return data
    }

    fun getForecast() {
        viewModelScope.launch {

        }
    }

}