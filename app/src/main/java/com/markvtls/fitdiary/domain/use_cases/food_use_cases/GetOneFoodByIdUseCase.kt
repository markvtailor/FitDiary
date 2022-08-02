package com.markvtls.fitdiary.domain.use_cases.food_use_cases

import com.markvtls.fitdiary.data.FoodServing
import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetOneFoodByIdUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(id: Int): Flow<FoodServing> {
        return repository.getFoodById(id)
    }
}