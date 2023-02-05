package ir.reversedev.mycalendar.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_favorite")
data class FavoriteEntity (
    @PrimaryKey
    val name: String ,
    val lat :Double ,
    val lon :Double ,
    @ColumnInfo(name = "selected" , defaultValue = "true")
    val selected : Boolean = true
)