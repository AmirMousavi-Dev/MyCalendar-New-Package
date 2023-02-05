package ir.reversedev.mycalendar.ui.calendar

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.aminography.primecalendar.persian.PersianCalendar
import dagger.android.support.DaggerFragment
import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.CalendarProperties.Companion.HORIZONTAL
import ir.reversedev.calendar.availablity.RangePriceSelectionAvailabilityRule
import ir.reversedev.calendar.calendar.RegionalType
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.model.DayRange
import ir.reversedev.calendar.model.agenda.AgendaDayRange
import ir.reversedev.calendar.types.singleselection.SingleSelection
import ir.reversedev.calendar.types.singleselection.SingleSelectionListener
import ir.reversedev.mycalendar.databinding.FragmentCalendarBinding
import ir.reversedev.mycalendar.model.data.NoteEntity
import ir.reversedev.mycalendar.util.RoomDateConverter
import java.util.*
import javax.inject.Inject

class CalendarFragment : DaggerFragment(), SingleSelectionListener, NoteAdapter.NoteEvent {
    private lateinit var _binding: FragmentCalendarBinding
    private lateinit var myAdapter : NoteAdapter
    @Inject
    lateinit var viewModelFactory: CalendarViewModel.Factory
    private lateinit var viewModel: CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this as Fragment ,
            viewModelFactory
        ) [CalendarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialAdapter()
        CalendarProperties(
            regionalType = RegionalType.Jalali,
            calendarType = SingleSelection(this),
            calendarOrientation = HORIZONTAL,
            availabilityRule = RangePriceSelectionAvailabilityRule(
                availableFromToday = true
            ),
            agendaRangeDays = listOf(
                AgendaDayRange(
                    title = "تست 1",
                    color = "#2d2d2d",
                    agendaRangeList = agendaRangeList1(RegionalType.Jalali)
                )
            )
        ).also { _binding.calendar.properties = it }
        _binding.calendar.getCurrentMonthPosition()
        _binding.calendar.submitNextDates(Calendar.MONTH, 500)
        val currentDay  = _binding.calendar.getCurrentMonthValue()
        val year = currentDay?.year ?: 1401
        val month = currentDay?.month ?: 1
        val day = currentDay?.day ?:1


        val date = year.civilDate(month , day)
        val longDate = RoomDateConverter.fromDate(date)
        viewModel.getAllNote(longDate!!)
        viewModel.notes.observe(this.viewLifecycleOwner) {
            it.ifNotHandled {
                myAdapter.setData(it)
            }
        }


    }


    private fun agendaRangeList1(regionalType: RegionalType) = listOf(
        DayRange(
            startDate = Day(1400, 12, 5, regionalType),
            endDate = Day(1400, 12, 12, regionalType)
        ),
        DayRange(
            startDate = Day(1400, 12, 15, regionalType),
            endDate = Day(1400, 12, 18, regionalType)
        )
    )


    override fun onDaySelected(day: Day?) {
        val year = day!!.year
        val month = day.month
        val day = day.day
        val date = PersianCalendar()
        date.set(year , month , day)
        Log.v("test" , date.toCivil().shortDateString)
        findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToNoteDialog(year , month , day ,null))
    }

    private fun initialAdapter() {
        val data = arrayListOf<NoteEntity>()
        myAdapter = NoteAdapter(data , this)
        _binding.recyclerCalendar.adapter = myAdapter
        _binding.recyclerCalendar.layoutManager = LinearLayoutManager(context , RecyclerView.VERTICAL , false)
        _binding.recyclerCalendar.recycledViewPool.setMaxRecycledViews(0,0)
    }

    override fun editClicked(noteEntity: NoteEntity) {
        findNavController().navigate(CalendarFragmentDirections.actionCalendarFragmentToNoteDialog(0,0,0,noteEntity))
    }

    override fun deleteClicked(noteEntity: NoteEntity, position: Int) {
        val dialog = SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
        dialog.titleText = "ایا میخواهید این مناسبت را حذف کنید ؟"
        dialog.confirmText = "بله"
        dialog.cancelText = "خیر"
        dialog.setConfirmClickListener {
            viewModel.deleteNote(noteEntity.id!!)
            dialog.dismiss()
        }
        dialog.setCancelClickListener {
            dialog.dismiss()
        }
        dialog.show()
        myAdapter.delete(noteEntity , position)

    }

    val civilDate : Int.(Int ,Int) -> Date = { month, day ->
        val persian = PersianCalendar()
        persian.set(this , month , day)
        val civil = persian.toCivil()
        civil.getTime()
    }
}