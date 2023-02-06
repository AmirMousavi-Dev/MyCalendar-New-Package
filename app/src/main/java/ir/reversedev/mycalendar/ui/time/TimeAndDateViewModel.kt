package ir.reversedev.mycalendar.ui.time

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.aminography.primecalendar.common.toCivil
import com.aminography.primecalendar.common.toHijri
import com.aminography.primecalendar.common.toPersian
import ir.reversedev.mycalendar.model.data.MonasebatEntity
import ir.reversedev.mycalendar.util.Event
import ir.reversedev.mycalendar.viewmodel.MainRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class TimeAndDateViewModel @Inject constructor (private val mainRepository: MainRepository) :
    ViewModel() {

    class Factory @Inject constructor(
        private val provider: Provider<TimeAndDateViewModel>
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return provider.get() as T
        }
    }

    private val mutableLiveTime = MutableLiveData<String>()
    val liveTime : LiveData<String>
        get() = mutableLiveTime
    private val _monasebat = MutableLiveData<Event<String>> ()
    val monasebat
        get() = _monasebat

    init {
        val timer = Timer()
        timer.schedule(
            object : TimerTask(){
                @SuppressLint("SimpleDateFormat")
                override fun run() {
                    val currentTime = Calendar.getInstance().time
                    val formatter = SimpleDateFormat("HH:mm:ss")
                    val current = formatter.format(currentTime)
                    mutableLiveTime.postValue(current)
                }

            } , 1000 ,1000
        )
    }

    fun persianDate(): List<String> {
        val calendar = Calendar.getInstance()
        val persian = calendar.toPersian()
        return listOf(
            persian.dayOfMonth.toString(),
            persian.monthName,
            persian.year.toString(),
            persian.weekDayName
        )
    }
    fun hijriDate(): List<String> {
        val calendar = Calendar.getInstance()
        val hijri = calendar.toHijri()
        return listOf(
            hijri.dayOfMonth.toString(),
            hijri.monthName,
            hijri.year.toString(),
            hijri.weekDayName
        )
    }
    fun civilDate(): List<String> {
        val calendar = Calendar.getInstance()
        val civil = calendar.toCivil()
        return listOf(
            civil.dayOfMonth.toString(),
            civil.monthName,
            civil.year.toString(),
            civil.weekDayName
        )
    }

    fun getMonasebat () {
        try {

        viewModelScope.launch {
            val data = mainRepository.getMonasebat()
            val monasebat = data[0].occasion
            _monasebat.value = Event(monasebat)
        }
    } catch (e :Exception) {
            _monasebat.value = Event("خطا در اتصال به سرور")
    }
        }
}