package ir.reversedev.mycalendar.util

import androidx.room.TypeConverter
import java.util.*

object RoomDateConverter {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return if (date == null) null else date.time
    }
}