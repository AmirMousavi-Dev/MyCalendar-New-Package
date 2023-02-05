package ir.reversedev.mycalendar.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ir.reversedev.mycalendar.model.data.CitiesEntity

@Dao
interface CityDao {

    @Insert
    suspend fun insertAll (city : CitiesEntity)
    @Query("SELECT DISTINCT state FROM tb_city")
    suspend fun getState () :List<String>

    @Query("SELECT * FROM tb_city WHERE State = :state")
    suspend fun getCities (state :String) :List<CitiesEntity.CitiesEntityItem>

}