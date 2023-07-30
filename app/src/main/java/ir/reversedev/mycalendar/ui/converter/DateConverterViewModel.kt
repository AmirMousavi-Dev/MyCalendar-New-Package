package ir.reversedev.mycalendar.ui.converter

import com.aminography.primecalendar.common.toCivil
import com.aminography.primecalendar.common.toHijri
import com.aminography.primecalendar.common.toPersian
import com.aminography.primecalendar.persian.PersianCalendar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import javax.inject.Inject

class DateConverterViewModel @Inject constructor() {

    private val _state = MutableStateFlow(FirstState())
    val state = _state.asStateFlow()

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

    fun convertDate (year :Int , month :Int ,day :Int) : PersianCalendar {
        val persian = PersianCalendar()
        persian.set(year , month , day)
        return persian
    }


}
class FirstState