package com.markvtls.fitdiary.food.domain.use_cases

import com.markvtls.fitdiary.food.data.dto.FoodNutrition
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
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