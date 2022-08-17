package com.markvtls.fitdiary.food.domain.repository

import com.markvtls.fitdiary.food.data.dto.FoodNutrition
import com.markvtls.fitdiary.food.data.source.local.FoodServing
import com.markvtls.fitdiary.food.data.dto.TranslationRequest
import com.markvtls.fitdiary.food.data.dto.TranslationResponse
import com.markvtls.fitdiary.food.domain.model.CaloriesForDay
import kotlinx.coroutines.flow.Flow

interface FoodServingRepository {

    suspend fun insertFood(foodServing: FoodServing)

    suspend fun deleteFood(id: Int)

    suspend fun translateName(json: TranslationRequest): TranslationResponse

    suspend fun getNutrition(name: String): List<FoodNutrition>

    fun getAllForCurrentWeek(dates: List<String>): Flow<List<CaloriesForDay>>
    
    fun getFoodById(id: Int): Flow<FoodServing>

    fun getFoodByDate(date: String): Flow<List<FoodServing>>
    
    fun getCcalSum(date: String): Flow<Int>

    fun getAll(): Flow<List<FoodServing>>

    
}