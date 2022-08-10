package com.markvtls.fitdiary.pedometer.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StepActivity::class], version = 1, exportSchema = false)
abstract class StepActivityDatabase: RoomDatabase() {

    abstract fun stepActivityDao(): StepActivityDao

    companion object {
        @Volatile
        private var INSTANCE: StepActivityDatabase? = null


        fun getDatabase(context: Context): StepActivityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StepActivityDatabase::class.java,
                    "step_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}