package ir.reversedev.mycalendar.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.reversedev.mycalendar.model.data.FavoriteEntity

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM tb_favorite WHERE name = :name")
    suspend fun deleteFavorite(name: String)

    @Query("UPDATE tb_favorite SET selected = :newValue WHERE selected = :oldValue ")
    suspend fun onSelected(newValue :Boolean = false ,oldValue : Boolean = true )

    @Query("UPDATE tb_favorite SET selected = :value WHERE name = :name")
    suspend fun selected(name :String,value: Boolean = true )

    @Query("SELECT * FROM tb_favorite WHERE selected = 1 ")
    suspend fun getFavorite () : FavoriteEntity

    @Query("SELECT * FROM tb_favorite")
    suspend fun getAllFavorite () :List<FavoriteEntity>
}