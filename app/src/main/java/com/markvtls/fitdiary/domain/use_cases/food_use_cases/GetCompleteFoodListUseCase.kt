package com.markvtls.fitdiary.domain.use_cases.food_use_cases

import com.markvtls.fitdiary.data.FoodServing
import com.markvtls.fitdiary.data.toDomain
import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCompleteFoodListUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(): Flow<List<FoodServing>> {
        return repository.getAll()
    }
}