package ir.reversedev.calendar.adapter.day

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import ir.reversedev.calendar.CalendarProperties
import com.calendar.adapter.day.viewholder.*
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.utils.dp
import ir.reversedev.calendar.adapter.day.viewholder.AgendaDaysPriceViewHolder
import ir.reversedev.calendar.adapter.day.viewholder.AgendaRangeDaysViewHolder
import ir.reversedev.calendar.adapter.day.viewholder.DayViewHolder
import ir.reversedev.calendar.adapter.day.viewholder.DayViewHolderType
import com.google.android.flexbox.FlexboxLayoutManager

internal class DaysAdapter(
    private val properties: CalendarProperties,
    private val daysAdapterListener: DaysAdapterListener
) : RecyclerView.Adapter<DayViewHolder>() {

    private val dayList = ArrayList<Day>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            properties.dayViewHolderType.layoutId,
            parent,
            false
        ).apply {
            updateLayoutParams<FlexboxLayoutManager.LayoutParams> {
                when (properties.dayViewHolderType) {
                    DayViewHolderType.AgendaRangeDaysViewHolder -> {
                        width = (parent.measuredWidth / 7)
                    }
                    else -> {
                        width = (parent.measuredWidth / 7) - dp(2)
                        setMargins(
                            dp(1),
                            dp(1),
                            dp(1),
                            dp(1)
                        )
                    }
                }
            }
        }

        return when (properties.dayViewHolderType) {
            DayViewHolderType.DayViewHolder -> DayViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
            DayViewHolderType.DayPriceViewHolder -> DayPriceViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
            DayViewHolderType.AgendaDaysPriceViewHolder -> AgendaDaysPriceViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
            DayViewHolderType.AgendaRangeDaysViewHolder -> AgendaRangeDaysViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
            else -> DayViewHolder(
                view = view,
                properties = properties,
                listener = daysAdapterListener
            )
        }
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        holder.bind(dayList[position], position)
    }

    override fun getItemCount(): Int = dayList.size

    fun submitList(list: List<Day>) {
        dayList.clear()
        dayList.addAll(list)
        notifyDataSetChanged()
    }
}