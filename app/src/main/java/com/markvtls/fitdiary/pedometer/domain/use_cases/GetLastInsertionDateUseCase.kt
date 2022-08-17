package com.markvtls.fitdiary.pedometer.domain.use_cases

import com.markvtls.fitdiary.pedometer.domain.repository.PedometerRepository
import javax.inject.Inject

class GetLastInsertionDateUseCase @Inject constructor(
    private val repository: PedometerRepository
) {
    operator fun invoke() =
        repository.getLastInsertionDate()
}