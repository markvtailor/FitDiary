package com.markvtls.fitdiary.domain.use_cases.food_use_cases

import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import javax.inject.Inject

class DeleteFoodUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteFood(id)
    }
}