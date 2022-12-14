package com.markvtls.fitdiary.pedometer.domain.use_cases

import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import com.markvtls.fitdiary.pedometer.domain.repository.PedometerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStepsByDateUseCase @Inject constructor(
    private val repository: PedometerRepository
) {
    operator fun invoke(date: String): Flow<StepActivity> {
        return repository.getStepsByDate(date)
    }

}