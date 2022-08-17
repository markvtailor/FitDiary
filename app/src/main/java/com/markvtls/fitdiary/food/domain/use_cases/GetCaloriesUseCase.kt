package com.markvtls.fitdiary.food.domain.use_cases

import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCaloriesUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(date: String): Flow<Int> {
        return repository.getCcalSum(date)
    }


}