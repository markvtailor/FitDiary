package com.markvtls.fitdiary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FoodServing::class], version = 1, exportSchema = false)
abstract class FoodServingDatabase: RoomDatabase() {

    abstract fun foodServingDao(): FoodServingDao

    companion object {
        @Volatile
        private var INSTANCE: FoodServingDatabase? = null


        fun getDatabase(context: Context): FoodServingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodServingDatabase::class.java,
                    "food_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}