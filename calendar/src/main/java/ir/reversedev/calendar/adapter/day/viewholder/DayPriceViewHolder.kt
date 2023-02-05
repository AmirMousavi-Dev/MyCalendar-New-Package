package com.calendar.adapter.day.viewholder

import android.view.View
import androidx.core.view.isVisible
import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.adapter.day.DaysAdapterListener
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.utils.PriceFormatter
import ir.reversedev.calendar.adapter.day.viewholder.DayViewHolder
import kotlin.math.abs

internal open class DayPriceViewHolder(
    view: View,
    properties: CalendarProperties,
    listener: DaysAdapterListener
) : DayViewHolder(view, properties, listener) {

    override fun bind(day: Day, position: Int) {
        super.bind(day, position)
    }

    private fun txtPriceVisibility(currentDay: Day) = properties.selectedCheckOut != currentDay

    private fun getPrice(day: Day): String {
        val price = if (day.discount != null && day.discount != 0.0)
            ((day.price ?: 0.0) - ((day.price ?: 0.0) * ((day.discount ?: 0.0) / 100))).div(1000)
        else
            (day.price ?: 0.0).div(1000)
        return if (price <= 0.0) "-" else PriceFormatter.formatPrice(abs(price))
    }
}