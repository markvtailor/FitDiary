package com.markvtls.fitdiary.domain.use_cases.food_use_cases

import com.markvtls.fitdiary.data.dto.FoodNutrition
import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetFoodNutritionUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(name: String): Flow<List<FoodNutrition>> = flow {
        val nutritionList = repository.getNutrition(name)
        emit(nutritionList)
    }
}