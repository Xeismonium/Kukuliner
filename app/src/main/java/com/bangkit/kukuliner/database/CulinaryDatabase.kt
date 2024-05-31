package com.bangkit.kukuliner.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CulinaryEntity::class], version = 1)
abstract class CulinaryRoomDatabase : RoomDatabase() {
    abstract fun culinaryDao(): CulinaryDao

    companion object {
        @Volatile
        private var INSTANCE: CulinaryRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): CulinaryRoomDatabase {
            if (INSTANCE == null) {
                synchronized(CulinaryRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CulinaryRoomDatabase::class.java, "culinary_database")
                        .build()
                }
            }
            return INSTANCE as CulinaryRoomDatabase
        }
    }
}