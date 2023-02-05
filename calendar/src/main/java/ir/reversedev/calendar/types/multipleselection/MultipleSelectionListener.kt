package ir.reversedev.calendar.types.multipleselection

import ir.reversedev.calendar.model.Day

interface MultipleSelectionListener {
    fun onSelectedDays(selectedDays: List<Day>)
}