package ir.reversedev.mycalendar.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.reversedev.mycalendar.model.data.NoteEntity

@Dao
interface NoteDao {

    @Insert
    suspend fun insertNote (noteEntity: NoteEntity)

    @Query("UPDATE tb_note SET title = :title  , detail = :detail WHERE id LIKE :id")
    suspend fun updateNote (title:String , detail :String  , id: Int)

    @Query("SELECT * FROM tb_note WHERE date >= :longDate ")
    suspend fun getAllNotes(longDate : Long) :List<NoteEntity>

    @Query("DELETE FROM tb_note WHERE id = :id")
    suspend fun deleteNote(id:Int)
}