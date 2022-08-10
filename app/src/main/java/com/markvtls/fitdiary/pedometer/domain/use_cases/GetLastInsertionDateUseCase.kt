package com.markvtls.fitdiary.pedometer.domain.use_cases

import com.markvtls.fitdiary.pedometer.domain.repository.StepActivityRepository
import javax.inject.Inject

class GetLastInsertionDateUseCase @Inject constructor(
    private val repository: StepActivityRepository
) {
    operator fun invoke() =
        repository.getLastInsertionDate()
}