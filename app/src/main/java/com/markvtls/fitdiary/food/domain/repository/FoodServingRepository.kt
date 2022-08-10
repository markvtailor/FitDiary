package com.markvtls.fitdiary.food.domain.repository

import com.markvtls.fitdiary.food.data.dto.FoodNutrition
import com.markvtls.fitdiary.food.data.source.local.FoodServing
import com.markvtls.fitdiary.food.data.dto.TranslationRequest
import com.markvtls.fitdiary.food.data.dto.TranslationResponse
import kotlinx.coroutines.flow.Flow

interface FoodServingRepository {

    suspend fun insertFood(foodServing: FoodServing)

    suspend fun deleteFood(id: Int)

    suspend fun translateName(json: TranslationRequest): TranslationResponse

    suspend fun getNutrition(name: String): List<FoodNutrition>
    
    fun getFoodById(id: Int): Flow<FoodServing>

    fun getFoodByDate(date: String): Flow<List<FoodServing>>
    
    fun getCcalSum(date: String): Flow<Int>

    fun getAll(): Flow<List<FoodServing>>

    
}