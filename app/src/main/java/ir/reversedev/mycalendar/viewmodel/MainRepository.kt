package ir.reversedev.mycalendar.viewmodel

import androidx.lifecycle.ViewModel
import ir.reversedev.mycalendar.model.apiservice.MonasebatApiService
import ir.reversedev.mycalendar.model.apiservice.OghatApiService
import ir.reversedev.mycalendar.model.apiservice.WeatherApiService
import ir.reversedev.mycalendar.model.dao.CityDao
import ir.reversedev.mycalendar.model.dao.FavoriteDao
import ir.reversedev.mycalendar.model.dao.NoteDao
import ir.reversedev.mycalendar.model.data.*
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val weatherApiService: WeatherApiService,
    private val oghatApiService: OghatApiService,
    private val monasebatApiService: MonasebatApiService,
    private val cityDao: CityDao,
    private val favoriteDao: FavoriteDao,
    private val noteDao: NoteDao
) : ViewModel() {

    // api service
    suspend fun getOghat(lat: Double, lon: Double): OghatEntity {
        return oghatApiService.getOghat(lat, lon)
    }

    suspend fun getWeather(lat: Double, lon: Double): WeatherEntity {
        return weatherApiService.getWeather(lat, lon)
    }

    suspend fun getForecast(lat: Double, lon: Double): ForecastEntity {
        return weatherApiService.getForecast(lat, lon)
    }

    suspend fun getMonasebat(): MonasebatEntity {
        return monasebatApiService.getMonasebat()
    }

    // local
    // city dao
    suspend fun insertAllCities(city: CitiesEntity) {
        cityDao.insertAll(city)
    }

    suspend fun getState(): List<String> {
        return cityDao.getState()
    }

    suspend fun getCities(state: String): List<CitiesEntity.CitiesEntityItem> {
        return cityDao.getCities(state)
    }

    // favorite dao
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity) {
        favoriteDao.insertFavorite(favoriteEntity)
    }

    suspend fun deleteFavorite(name: String) {
        favoriteDao.deleteFavorite(name)
    }

    suspend fun onSelected(newValue: Boolean = false, oldValue: Boolean = true) {
        favoriteDao.onSelected()
    }

    suspend fun selected(name: String, value: Boolean = true) {
        favoriteDao.selected(name)
    }

    suspend fun getFavorite(): FavoriteEntity {
        return favoriteDao.getFavorite()
    }

    suspend fun getAllFavorite(): List<FavoriteEntity> {
        return favoriteDao.getAllFavorite()
    }

    // note Dao
    suspend fun insertNote(noteEntity: NoteEntity) {
        noteDao.insertNote(noteEntity)
    }

    suspend fun updateNote(title: String, detail: String, id: Int) {
        noteDao.updateNote(title, detail, id)
    }

    suspend fun getAllNotes(longDate: Long): List<NoteEntity> {
        return noteDao.getAllNotes(longDate)
    }

    suspend fun deleteNote(id: Int) {
        noteDao.deleteNote(id)
    }


}