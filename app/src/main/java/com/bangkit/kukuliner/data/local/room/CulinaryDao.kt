package com.bangkit.kukuliner.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bangkit.kukuliner.data.local.entity.CulinaryEntity

@Dao
interface CulinaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(culinary: List<CulinaryEntity>)

    @Update
    suspend fun update(culinary: CulinaryEntity)

    @Delete
    suspend fun delete(culinary: CulinaryEntity)

    @Query("SELECT * FROM culinary")
    fun getCulinary(): LiveData<List<CulinaryEntity>>

    @Query("SELECT * FROM culinary WHERE isFavorite = 1")
    fun getFavoriteCulinary(): LiveData<List<CulinaryEntity>>

    @Query("DELETE FROM culinary WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM culinary WHERE id = :id AND isFavorite = 1)")
    suspend fun isFavorite(id: String): Boolean
}