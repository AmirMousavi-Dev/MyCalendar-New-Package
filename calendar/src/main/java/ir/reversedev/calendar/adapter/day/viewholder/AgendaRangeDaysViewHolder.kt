package ir.reversedev.calendar.adapter.day.viewholder

import android.graphics.Typeface
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.adapter.day.DaysAdapterListener
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.utils.setBackgroundFromDrawable
import ir.reversedev.calendar.utils.setMutableDrawableColor
import ir.reversedev.calendar.R

internal class AgendaRangeDaysViewHolder(
    view: View,
    properties: CalendarProperties,
    listener: DaysAdapterListener
) : DayViewHolder(view, properties, listener) {
    private val bgAgendaDayRange: FrameLayout = view.findViewById(R.id.bg_agenda_day_range)
    private val imgStartAgenda: AppCompatImageView = view.findViewById(R.id.img_start_day_agenda)
    private val imgEndAgenda: AppCompatImageView = view.findViewById(R.id.img_end_day_agenda)

    override fun bind(day: Day, position: Int) {
        initRangeAgendaDays(day)
        super.bind(day, position)
    }

    private fun initRangeAgendaDays(day: Day) {
        val startAgenda = getStartAgendaRange(day)
        val middleAgenda = getMiddleAgendaRange(day)
        val endAgenda = getEndAgendaRange(day)

        val isDaySelected = properties.calendarType.isDaySelected(
            day,
            properties
        )

        val isAgendaRange = startAgenda != null || middleAgenda != null || endAgenda != null
        val startAgendaVisibility = !isDaySelected && startAgenda != null
        val middleAgendaVisibility = !isDaySelected && middleAgenda != null
        val endAgendaVisibility = !isDaySelected && endAgenda != null

        if (isAgendaRange) txtDay.typeface = Typeface.DEFAULT_BOLD
        else txtDay.typeface = Typeface.DEFAULT

        val background = when {
            startAgendaVisibility -> R.drawable.bg_day_dashed_stroke_start
            middleAgendaVisibility -> R.drawable.bg_day_dashed_stroke_middle
            endAgendaVisibility -> R.drawable.bg_day_dashed_stroke_end
            else -> 0
        }
        if (background == 0) bgAgendaDayRange.setBackgroundResource(R.color.white)
        else bgAgendaDayRange.setBackgroundFromDrawable(background)

        imgStartAgenda.isVisible = startAgendaVisibility
        imgEndAgenda.isVisible = endAgendaVisibility
        imgStartAgenda.setMutableDrawableColor(
            startAgenda?.getAgendaColor()
        )
        imgEndAgenda.setMutableDrawableColor(
            endAgenda?.getAgendaColor()
        )
    }

    private fun getStartAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.firstOrNull { range ->
            currentDay == range.startDate
        } != null
    }

    private fun getMiddleAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.firstOrNull { range ->
            currentDay > range.startDate && currentDay < range.endDate
        } != null
    }

    private fun getEndAgendaRange(currentDay: Day) = properties.agendaRangeDays.firstOrNull {
        it.agendaRangeList.firstOrNull { range ->
            currentDay == range.endDate
        } != null
    }
}