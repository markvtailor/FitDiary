package com.markvtls.fitdiary.data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class FoodServingRepository(private val database: FoodServingDatabase) {


    suspend fun insertFood(foodServing: FoodServing) {
        database.foodServingDao().insert(foodServing)
    }
    suspend fun deleteFood(id: Int) {
        database.foodServingDao().deleteById(IdHolder(id))
    }
    fun getFoodById(id: Int): Flow<FoodServing> {
       return database.foodServingDao().getOneById(id)
    }
    fun getFoodByDate(date: String): Flow<List<FoodServing>> {
        return database.foodServingDao().getAllByDate(date)
    }
    fun getCcalSum(date: String): Flow<Int> {
       return database.foodServingDao().getTotalCcalByDate(date)
    }
    val foodList: Flow<List<FoodServing>> = database.foodServingDao().getAll()

}