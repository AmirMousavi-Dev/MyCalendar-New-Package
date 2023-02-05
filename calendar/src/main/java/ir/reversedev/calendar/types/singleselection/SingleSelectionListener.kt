package ir.reversedev.calendar.types.singleselection

import ir.reversedev.calendar.model.Day

interface SingleSelectionListener {
    fun onDaySelected(day: Day?)
}