package ir.reversedev.calendar.types.multipleselection

import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.R
import ir.reversedev.calendar.adapter.day.viewholder.DayViewHolder
import ir.reversedev.calendar.adapter.day.DaysAdapterListener
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.types.CalendarType
import ir.reversedev.calendar.utils.setBackgroundFromDrawable

class MultipleSelection(
    private val multipleSelectionListener: MultipleSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { viewHolder, dayItem, properties, listener ->
            if (checkAvailability(viewHolder, dayItem, properties)) {
                onDayClicked(properties, dayItem, multipleSelectionListener)
                listener.onDaysNotifyDataSetChanged()
            }
        }

    override fun dayBackground(
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties
    ) {
        if (checkAvailability(viewHolder, day, properties)) {
            viewHolder.bgDay.setBackgroundFromDrawable(
                getDayBackground(
                    currentDay = day,
                    selectedDays = properties.selectedMultipleDay
                )
            )
        }
    }

    override fun isDaySelected(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean = properties.selectedMultipleDay.any {
        it == currentDay
    }

    private fun getDayBackground(
        currentDay: Day,
        selectedDays: ArrayList<Day>?
    ): Int {
        return if (selectedDays?.any { it == currentDay } == true) {
            R.drawable.bg_day_selected
        } else R.drawable.bg_day
    }

    private fun onDayClicked(
        property: CalendarProperties,
        currentDay: Day,
        listener: MultipleSelectionListener
    ) {
        property.apply {
            if (property.selectedMultipleDay.any { it == currentDay }) {
                property.selectedMultipleDay.remove(currentDay)
            } else {
                property.selectedMultipleDay.add(currentDay)
            }
            listener.onSelectedDays(property.selectedMultipleDay)
        }
    }
}