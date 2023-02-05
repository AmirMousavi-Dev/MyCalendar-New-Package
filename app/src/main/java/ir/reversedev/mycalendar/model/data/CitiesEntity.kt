package ir.reversedev.mycalendar.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

class CitiesEntity : ArrayList<CitiesEntity.CitiesEntityItem>(){
    @Entity(tableName = "tb_city")
    data class CitiesEntityItem(
        val City: String,
        val State: String,
        val lat: Double,
        val lon: Double,
        @PrimaryKey(autoGenerate = true)
        val id: Int ? = null
    )
}