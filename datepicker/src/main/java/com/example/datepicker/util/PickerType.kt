package com.example.datepicker.util

sealed class PickerType{
    object Year: PickerType()
    object Month: PickerType()
    object Day: PickerType()
}
