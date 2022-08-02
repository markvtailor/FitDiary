package com.markvtls.fitdiary.domain.use_cases.food_use_cases

import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCaloriesUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(date: String): Flow<Int>  {
        return repository.getCcalSum(date)
    }


}