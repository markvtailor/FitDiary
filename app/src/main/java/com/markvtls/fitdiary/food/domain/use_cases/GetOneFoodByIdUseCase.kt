package com.markvtls.fitdiary.food.domain.use_cases

import com.markvtls.fitdiary.food.data.source.local.FoodServing
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOneFoodByIdUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(id: Int): Flow<FoodServing> {
        return repository.getFoodById(id)
    }
}