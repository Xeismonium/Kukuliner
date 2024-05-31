package com.bangkit.kukuliner.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CulinaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(culinary: List<CulinaryEntity>)

    @Update
    suspend fun update(culinary: CulinaryEntity)

    @Delete
    suspend fun delete(culinary: CulinaryEntity)

    @Query("SELECT * FROM culinary ORDER BY name ASC")
    fun getCulinary(): LiveData<List<CulinaryEntity>>

    @Query("SELECT * FROM culinary WHERE isFavorite = 1")
    fun getFavoriteCulinary(): LiveData<List<CulinaryEntity>>

    @Query("DELETE FROM culinary WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM culinary WHERE name = :name AND isFavorite = 1)")
    suspend fun isFavorite(name: String): Boolean
}