package ir.reversedev.calendar.types.rangeslelection

import ir.reversedev.calendar.model.Day

interface RangeSelectionListener{
    fun onCheckInSelected(day: Day)
    fun onCheckOutSelected(day: Day)
    fun onSelectsRemoved()
}