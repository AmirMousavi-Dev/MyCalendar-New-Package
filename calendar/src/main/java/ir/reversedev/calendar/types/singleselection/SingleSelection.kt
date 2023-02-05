package ir.reversedev.calendar.types.singleselection

import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.adapter.day.viewholder.DayViewHolder
import ir.reversedev.calendar.adapter.day.DaysAdapterListener
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.types.CalendarType
import ir.reversedev.calendar.utils.setBackgroundFromDrawable
import ir.reversedev.calendar.R

class SingleSelection(
    private val singleSelectionListener: SingleSelectionListener
) : CalendarType() {

    override val onDayClickListener: (
        viewHolder: DayViewHolder,
        day: Day,
        properties: CalendarProperties,
        listener: DaysAdapterListener
    ) -> Unit
        get() = { viewHolder, day, properties, listener ->
            if (checkAvailability(viewHolder, day, properties)) {
                onDayClicked(properties, day, singleSelectionListener)
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
                    selectedSingle = properties.selectedSingle
                )
            )
        }
    }

    override fun isDaySelected(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean = properties.selectedSingle != null && properties.selectedSingle == currentDay

    private fun getDayBackground(
        currentDay: Day,
        selectedSingle: Day?
    ): Int {
        return if (selectedSingle != null) {
            when (selectedSingle) {
                currentDay -> R.drawable.bg_day_circular_single_selected
                else -> R.drawable.bg_day
            }
        } else {
            R.drawable.bg_day
        }
    }

    private fun onDayClicked(
        property: CalendarProperties,
        currentDay: Day,
        listener: SingleSelectionListener
    ) {
        property.apply {
            if (selectedSingle == currentDay) {
                selectedSingle = null
                listener.onDaySelected(null)
            } else {
                selectedSingle = currentDay
                listener.onDaySelected(currentDay)
            }
        }
    }

}