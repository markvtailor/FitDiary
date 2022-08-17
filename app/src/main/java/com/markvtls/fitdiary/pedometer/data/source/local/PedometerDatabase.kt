package com.markvtls.fitdiary.pedometer.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StepActivity::class], version = 1, exportSchema = false)
abstract class PedometerDatabase : RoomDatabase() {

    abstract fun stepActivityDao(): PedometerDao

    companion object {
        @Volatile
        private var INSTANCE: PedometerDatabase? = null


        fun getDatabase(context: Context): PedometerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PedometerDatabase::class.java,
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