package com.markvtls.fitdiary.food.domain.use_cases

import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import javax.inject.Inject

class DeleteFoodUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteFood(id)
    }
}