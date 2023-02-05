package ir.reversedev.mycalendar.ui.mainscreen

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import ir.reversedev.mycalendar.model.data.CitiesEntity
import ir.reversedev.mycalendar.model.data.FavoriteEntity
import ir.reversedev.mycalendar.viewmodel.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor (private val context: Context, private val mainRepository: MainRepository) :
    ViewModel() {
    init {

    }
    fun insertCities (){
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("calendar" , Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("first_run" , true)) {
            val fileInString = context.applicationContext
                .assets
                .open("iran_cities.json")
                .bufferedReader()
                .use { it.readText() }
            val gson = Gson()
            val dataCities = gson.fromJson(fileInString , CitiesEntity::class.java)
            viewModelScope.launch {
                mainRepository.insertAllCities(dataCities)

                mainRepository.insertFavorite(FavoriteEntity("تهران" , 35.81037902832031, 51.464759826660156))
            }
        }
        sharedPreferences.edit().putBoolean("first_run" , false).apply()
    }
}
