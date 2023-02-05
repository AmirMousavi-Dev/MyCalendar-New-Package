package ir.reversedev.mycalendar.model.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tb_note")
data class NoteEntity(
    val title :String ,
    val detail : String ,
    val year :Int,
    val month :Int,
    val day :Int,
    val date : Long ,
    @PrimaryKey(autoGenerate = true)
    val id : Int? =null
) : Parcelable
