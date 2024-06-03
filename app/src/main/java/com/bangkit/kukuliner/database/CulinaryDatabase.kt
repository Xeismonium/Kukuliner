package com.bangkit.kukuliner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CulinaryEntity::class], version = 1, exportSchema = false)
abstract class CulinaryRoomDatabase : RoomDatabase() {
    abstract fun culinaryDao(): CulinaryDao

    companion object {
        @Volatile
        private var instance: CulinaryRoomDatabase? = null
        fun getInstance(context: Context): CulinaryRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    CulinaryRoomDatabase::class.java, "culinary_database.db"
                ).build().also { instance = it }
            }
    }
}