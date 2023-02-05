package ir.reversedev.mycalendar.ui.oghat

import android.util.Log
import androidx.lifecycle.*
import ir.reversedev.mycalendar.model.data.CitiesEntity
import ir.reversedev.mycalendar.model.data.FavoriteEntity
import ir.reversedev.mycalendar.model.data.OghatEntity
import ir.reversedev.mycalendar.util.Event
import ir.reversedev.mycalendar.viewmodel.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Provider

class OghatShareiViewModel @Inject constructor(private val mainRepository: MainRepository) :
    ViewModel() {

    class Factory @Inject constructor(
        val provider: Provider<OghatShareiViewModel>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return provider.get() as T
        }
    }

    private val _state = MutableLiveData<Event<List<String>>>()
    val state : LiveData<Event<List<String>>>
        get() = _state
    private val _cities = MutableLiveData<Event<List<CitiesEntity.CitiesEntityItem>>>()
    val cities : LiveData<Event<List<CitiesEntity.CitiesEntityItem>>>
        get() = _cities
    private val _favorite = MutableLiveData<Event<FavoriteEntity>> ()
    val favorite : LiveData<Event<FavoriteEntity>>
        get() = _favorite
    private val _favorites = MutableLiveData<Event<List<FavoriteEntity>>> ()
    val favorites : LiveData<Event<List<FavoriteEntity>>>
        get() = _favorites
    private val _oghat = MutableLiveData<Event<OghatEntity>>()
    val oghat : LiveData<Event<OghatEntity>>
        get() = _oghat
    private val _process = MutableLiveData<Event<Boolean>> ()
    val process : LiveData<Event<Boolean>>
        get() = _process
    var name : String? = null

    fun getState() {
        viewModelScope.launch(Dispatchers.Main) {
            val data = mainRepository.getState()
            _state.value = Event(data)
        }
    }
    fun getCities (state :String) {
        viewModelScope.launch {
            val data = mainRepository.getCities(state)
            _cities.value = Event(data)
        }

    }

    fun insertFavorite (favoriteEntity: FavoriteEntity) {
        viewModelScope.launch { mainRepository.insertFavorite(favoriteEntity)
        }
    }
    fun deleteFavorite (name :String) {
        viewModelScope.launch {
            mainRepository.deleteFavorite(name)
        }
    }
    fun getAllFavorite() {
        viewModelScope.launch {
            val favorites = mainRepository.getAllFavorite()
            _favorites.value = Event(favorites)
        }
    }
    fun selected(name: String) {
        viewModelScope.launch {
            mainRepository.selected(name)
        }
    }
    fun onSelected () {
        viewModelScope.launch {
            mainRepository.onSelected()
        }
    }

    fun getOghat () {
        viewModelScope.launch {
            try {


                val favorite = mainRepository.getFavorite()
                val oghat = mainRepository.getOghat(favorite.lat, favorite.lon)
                _favorite.value = Event(favorite)
                _oghat.value = Event(oghat)
            } catch (e : Exception) {
                getOghat()
                Log.v("test" , e.message ?: "null")
            }
        }

    }


}
