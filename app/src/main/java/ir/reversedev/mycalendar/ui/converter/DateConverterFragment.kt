package ir.reversedev.mycalendar.ui.converter

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aminography.primecalendar.civil.CivilCalendar
import com.aminography.primecalendar.common.toCivil
import com.aminography.primecalendar.common.toHijri
import com.aminography.primecalendar.hijri.HijriCalendar
import dagger.android.support.DaggerFragment
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.api.PersianPickerDate
import ir.hamsaa.persiandatepicker.api.PersianPickerListener
import ir.reversedev.mycalendar.R
import ir.reversedev.mycalendar.databinding.FragmentDateConverterBinding
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateConverterFragment : DaggerFragment() {
    private lateinit var _binding : FragmentDateConverterBinding
    @Inject
    lateinit var viewModel : DateConverterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDateConverterBinding.inflate(inflater , container , false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val persian = viewModel.persianDate()
        val hijri = viewModel.hijriDate()
        val civil = viewModel.civilDate()
        with(_binding) {

            txtNumberShamsiDC.text = persian[0]
            txtMonthShamsiDC.text = persian[1]
            txtYearShamsiDC.text = persian[2]
            txtDayShamsiDC.text = persian[3]

            txtSelectedDay.text = persian[3].setCurrentDate(persian[0] ,persian[1],persian[2])

            txtNumberGhamariDC.text = hijri[0]
            txtMonthGhamariDC.text = hijri[1]
            txtYearGhamariDC.text = hijri[2]
            txtDayGhamariDC.text = hijri[3]

            txtNumberMiladiDC.text = civil[0]
            txtMonthMiladiDC.text = civil[1]
            txtYearMiladiDC.text = civil[2]
            txtDayMiladiDC.text = civil[3]
        }

        val picker = PersianDatePickerDialog(context)
            .setPositiveButtonString("تبدیل تاریخ")
            .setNegativeButton("برگشت")
            .setTodayButton("تاریخ امروز")
            .setTodayButtonVisible(true)
            .setMinYear(1300)
            .setMaxYear(1500)
            .setMaxMonth(12)
            .setMaxDay(31)
            .setActionTextColor(Color.BLACK)
            .setBackgroundColor(Color.LTGRAY)
            .setPickerBackgroundColor(Color.LTGRAY)
            .setInitDate(PersianDatePickerDialog.THIS_YEAR , PersianDatePickerDialog.THIS_MONTH , PersianDatePickerDialog.THIS_DAY)
            .setActionTextColor(R.style.Theme_MyCalendar)
            .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
            .setShowInBottomSheet(true)
            .setListener(object : PersianPickerListener {
                override fun onDateSelected(persianPickerDate: PersianPickerDate) {
                    val year = persianPickerDate.gregorianYear
                    val month = persianPickerDate.gregorianMonth
                    val day = persianPickerDate.gregorianDay

                    with(_binding) {

                        txtNumberShamsiDC.text = persianPickerDate.persianDay.toString()
                        txtMonthShamsiDC.text = persianPickerDate.persianMonthName
                        txtYearShamsiDC.text = persianPickerDate.persianYear.toString()
                        txtDayShamsiDC.text = persianPickerDate.persianDayOfWeekName

                        val newHijri = hijri(year , month , day)
                        val newCivil = civil(year , month , day)
                        txtNumberGhamariDC.text = newHijri.dayOfMonth.toString()
                        txtMonthGhamariDC.text = newHijri.monthName
                        txtYearGhamariDC.text = newHijri.year.toString()
                        txtDayGhamariDC.text = newHijri.weekDayName

                        txtNumberMiladiDC.text = newCivil.dayOfMonth.toString()
                        txtMonthMiladiDC.text =newCivil.monthName
                        txtYearMiladiDC.text  =newCivil.dayOfMonth.toString()
                        txtDayMiladiDC.text =newCivil.weekDayName

                        txtSelectedDay.text= persianPickerDate.persianLongDate
                    }
                    val currentTime = Calendar.getInstance().time
                    val mDateFormat = SimpleDateFormat("MM/dd/yyyy")
                    val date = mDateFormat.format(currentTime)
                    val newDate = mDateFormat.parse(date)
                    if (newDate.time < persianPickerDate.timestamp) {
                        val mDifference = kotlin.math.abs(newDate.time - persianPickerDate.timestamp)
                        val difference  = mDifference / (24 * 60 * 60 * 1000)
                        if (difference.toInt() == 0) {
                            _binding.txtShowDifrence.text = "امروز"
                        } else {
                            _binding.txtShowDifrence.text = "$difference روز بعد "
                        }
                    } else {
                        val mDifference = kotlin.math.abs( persianPickerDate.timestamp-newDate.time)
                        Log.v("Amiremoon" , mDifference.toString())
                        val difference  = mDifference / (24 * 60 * 60 * 1000) +1

                        _binding.txtShowDifrence.text = "$difference روز قبل "

                    }

                }

                override fun onDismissed() {}
            })


        _binding.cvShowDate.setOnClickListener {
            picker.show()
        }

    }
    private fun hijri(year: Int, month: Int, day: Int): HijriCalendar {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.toHijri()
    }
    private fun civil(year: Int, month: Int, day: Int): CivilCalendar {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar.toCivil()
    }
    private val setCurrentDate :String.(String ,String , String) -> String = {day , month , year ->
        "$this  $day  $month  $year"
    }

}