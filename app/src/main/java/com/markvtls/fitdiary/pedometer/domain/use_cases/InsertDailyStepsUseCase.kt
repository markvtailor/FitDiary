package com.markvtls.fitdiary.pedometer.domain.use_cases

import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import com.markvtls.fitdiary.pedometer.domain.repository.StepActivityRepository
import javax.inject.Inject

class InsertDailyStepsUseCase @Inject constructor(
    private val repository: StepActivityRepository
) {

    suspend operator fun invoke(date: String, steps: Int) {
        val stepActivity = StepActivity(date, steps)
        repository.insertDailySteps(stepActivity)
    }

}