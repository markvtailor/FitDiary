package com.markvtls.fitdiary.food.domain.use_cases

import com.markvtls.fitdiary.food.data.source.local.FoodServing
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCompleteFoodListUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(): Flow<List<FoodServing>> {
        return repository.getAll()
    }
}