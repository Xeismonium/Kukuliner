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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(culinary: Culinary)

    @Update
    suspend fun update(culinary: Culinary)

    @Delete
    suspend  fun delete(culinary: Culinary)

    @Query("SELECT * FROM culinary ORDER BY name ASC")
    fun getAllCulinary(): LiveData<List<Culinary>>
}