package ir.reversedev.calendar.types.withoutselection

import com.calendar.types.withoutselection.WithoutSelectionListener
import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.adapter.day.viewholder.DayViewHolder
import ir.reversedev.calendar.adapter.day.DaysAdapterListener
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.types.CalendarType
import ir.reversedev.calendar.utils.setBackgroundFromDrawable
import ir.reversedev.calendar.R

class WithoutSelection(
    private val withoutSelectionListener: WithoutSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { _, _, _, _ -> }

    override fun dayBackground(
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties
    ) {
        if (checkAvailability(viewHolder, day, properties)) {
            viewHolder.bgDay.setBackgroundFromDrawable(R.drawable.bg_day)
        }
    }

    override fun isDaySelected(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean = false

}