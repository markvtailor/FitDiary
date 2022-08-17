package com.markvtls.fitdiary.pedometer.domain.use_cases

import com.markvtls.fitdiary.pedometer.data.source.local.toDomain
import com.markvtls.fitdiary.pedometer.domain.model.StepActivityDomain
import com.markvtls.fitdiary.pedometer.domain.repository.StepActivityRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetAllStepsUseCase @Inject constructor(
    private val repository: StepActivityRepository
) {
    operator fun invoke(): Flow<List<StepActivityDomain>> = flow {
        val data = repository.getAll()
        val steps = mutableListOf<StepActivityDomain>()
        data.collect { list ->
            list.forEach {
                steps.add(it.toDomain())
            }
            emit(steps)
             }
        steps.forEach {
            println(it.calories)
        }
        //emit(steps)
    }


}