package ir.reversedev.mycalendar.ui.calendar

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aminography.primecalendar.persian.PersianCalendar
import ir.reversedev.mycalendar.model.data.NoteEntity
import ir.reversedev.mycalendar.util.Event
import ir.reversedev.mycalendar.util.RoomDateConverter
import ir.reversedev.mycalendar.viewmodel.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class CalendarViewModel @Inject constructor(val mainRepository: MainRepository) : ViewModel() {
    private val _notes = MutableLiveData<Event<List<NoteEntity>>> ()
    val notes
        get() = _notes

    class Factory @Inject constructor(
        val provider: Provider<CalendarViewModel>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return provider.get() as T
        }
    }

    fun getAllNote (longDate :Long) {
        viewModelScope.launch {
            val data = mainRepository.getAllNotes(longDate)
            _notes.value = Event(data)
        }
    }

    fun updateNote (title:String , detail :String  , id :Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.updateNote(title , detail , id)
        }
    }
    fun insertNote (title:String , detail :String  , year: Int , month: Int , day: Int) {

        val persianCalendar = PersianCalendar()
        persianCalendar.set(year , month ,day)
        val civil = persianCalendar.toCivil()
        val date = civil.getTime()
        val longDate = RoomDateConverter.fromDate(date)
        Log.v("test" , longDate.toString())
        Log.v("test" , RoomDateConverter.toDate(longDate).toString())
        val note = NoteEntity(title, detail,year , month , day ,longDate!!)
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.insertNote(note)
        }
    }
    fun deleteNote (id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.deleteNote(id)
        }
    }

}