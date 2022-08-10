package com.markvtls.fitdiary.food.domain.use_cases

import com.markvtls.fitdiary.food.data.source.local.FoodServing
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoodListByDateUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(date: String): Flow<List<FoodServing>> {
        return repository.getFoodByDate(date)
    }
}