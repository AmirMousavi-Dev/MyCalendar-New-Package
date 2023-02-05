package ir.reversedev.calendar.adapter.day.viewholder

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.adapter.day.DaysAdapterListener
import com.calendar.adapter.day.viewholder.DayPriceViewHolder
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.utils.setMutableDrawableColor
import ir.reversedev.calendar.R

internal class AgendaDaysPriceViewHolder(
    view: View,
    properties: CalendarProperties,
    listener: DaysAdapterListener
) : DayPriceViewHolder(view, properties, listener) {
    private val imgDayAgenda: AppCompatImageView = view.findViewById(R.id.img_day_agenda)

    override fun bind(day: Day, position: Int) {
        initAgendaDay(day)
        super.bind(day, position)
    }

    private fun initAgendaDay(day: Day) {
        val startAgenda = getStartAgenda(day)
        imgDayAgenda.isVisible =
            !properties.calendarType.isDaySelected(day, properties) && startAgenda != null

        imgDayAgenda.setMutableDrawableColor(
            startAgenda?.getAgendaColor()
        )
    }

    private fun getStartAgenda(currentDay: Day) = properties.agendaDays.firstOrNull {
        it.agendaList.firstOrNull { day -> day == currentDay } != null
    }
}