package ir.reversedev.calendar.availablity

import ir.reversedev.calendar.CalendarProperties
import ir.reversedev.calendar.model.Day
import ir.reversedev.calendar.model.DayStatus

open class BaseAvailabilityRule(
    private val availableFromToday: Boolean
) {

    open fun isAvailable(
        currentDay: Day,
        properties: CalendarProperties
    ): Boolean {
        val fromToday = checkAvailabilityFromToday(currentDay, properties.getToday())
        return when (availableFromToday) {
            true -> fromToday && currentDay.status == DayStatus.AVAILABLE
            false -> currentDay.status == DayStatus.AVAILABLE
        }
    }

    fun checkAvailabilityFromToday(
        currentDay: Day,
        today: Day
    ) = currentDay >= today
}