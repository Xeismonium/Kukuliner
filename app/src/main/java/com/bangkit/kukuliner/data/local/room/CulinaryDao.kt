package com.bangkit.kukuliner.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bangkit.kukuliner.data.remote.response.CulinaryResponseItem

@Dao
interface CulinaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(culinary: List<CulinaryResponseItem>)

    @Update
    suspend fun update(culinary: CulinaryResponseItem)

    @Delete
    suspend fun delete(culinary: CulinaryResponseItem)

    @Query("SELECT * FROM culinary")
    fun getCulinary(): LiveData<List<CulinaryResponseItem>>

    @Query("SELECT * FROM culinary WHERE nama LIKE '%' || :query || '%'")
    fun searchCulinary(query: String): LiveData<List<CulinaryResponseItem>>

    @Query("SELECT * FROM culinary WHERE isFavorite = 1")
    fun getFavoriteCulinary(): LiveData<List<CulinaryResponseItem>>

    @Query("SELECT * FROM culinary WHERE isFavorite = 1 AND nama LIKE '%' || :query || '%'")
    fun searchFavoriteCulinary(query: String): LiveData<List<CulinaryResponseItem>>

    @Query("DELETE FROM culinary WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM culinary WHERE id = :id AND isFavorite = 1)")
    suspend fun isFavorite(id: String): Boolean
}