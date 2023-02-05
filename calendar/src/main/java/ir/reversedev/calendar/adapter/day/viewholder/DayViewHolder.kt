package ir.reversedev.calendar.adapter.day.viewholder

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.R
import ir.reversedev.calendar.adapter.day.DaysAdapterListener
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.utils.SquareView

internal open class DayViewHolder(
    view: View,
    protected val properties: CalendarProperties,
    protected val listener: DaysAdapterListener
) : RecyclerView.ViewHolder(view) {
    val context: Context = view.context
    val bgDay: SquareView = view.findViewById(R.id.bg_day)
    val txtDay: AppCompatTextView = view.findViewById(R.id.txt_day)

    open fun bind(day: Day, position: Int) {
        this.itemView.visibility = dayVisibility(day.day)
        textColor(day, position)
        txtDay.text = day.day.toString()
        bgDay.setOnClickListener {
            properties.calendarType.onDayClickListener.invoke(this, day, properties, listener)
        }
        properties.calendarType.bind(
            this,
            day,
            properties,
            listener
        )
    }

    private fun dayVisibility(day: Int) = if (day != -1) View.VISIBLE else View.INVISIBLE

    private fun textColor(currentDay: Day, position: Int) {
        val color = ContextCompat.getColor(
            context,
            when {
                position == 6 -> R.color.red
                position == 13 -> R.color.red
                position == 20 -> R.color.red
                position == 27 -> R.color.red
                position == 34 -> R.color.red
                currentDay.isHoliday -> R.color.red
                else -> {
                    if (properties.calendarType.isDaySelected(
                            currentDay,
                            properties
                        )
                    ) R.color.blue
                    else R.color.secondary
                }
            }
        )
        txtDay.setTextColor(color)
    }
}