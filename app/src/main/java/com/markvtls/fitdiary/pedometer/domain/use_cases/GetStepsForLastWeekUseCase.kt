package com.markvtls.fitdiary.pedometer.domain.use_cases

import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import com.markvtls.fitdiary.pedometer.domain.repository.StepActivityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStepsForLastWeekUseCase @Inject constructor(
        private val repository: StepActivityRepository
    ) {
    operator fun invoke(): Flow<List<StepActivity>> {
        return repository.getAllForCurrentWeek()
    }
}