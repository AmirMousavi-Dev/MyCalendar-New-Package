package ir.reversedev.mycalendar.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.reversedev.mycalendar.model.dao.CityDao
import ir.reversedev.mycalendar.model.dao.FavoriteDao
import ir.reversedev.mycalendar.model.dao.NoteDao
import ir.reversedev.mycalendar.model.data.CitiesEntity
import ir.reversedev.mycalendar.model.data.FavoriteEntity
import ir.reversedev.mycalendar.model.data.NoteEntity

@Database(
    entities = [CitiesEntity.CitiesEntityItem::class, FavoriteEntity::class , NoteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MyDataBase : RoomDatabase() {

    abstract val cityDao: CityDao
    abstract val favoriteDao: FavoriteDao
    abstract val noteDao: NoteDao

    companion object {
        var database: MyDataBase? = null
        fun getDatabase(context: Context): MyDataBase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context,
                    MyDataBase::class.java,
                    "calendarDatabase"
                ).build()
            }
            return database!!
        }
    }
}