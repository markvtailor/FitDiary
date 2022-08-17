package com.markvtls.fitdiary.profile.domain.use_cases

import android.content.Context
import com.markvtls.fitdiary.profile.domain.repository.UserProfileRepository
import javax.inject.Inject

class SavePreferencesUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(ccalGoal: Int, stepsGoal: Int, context: Context) {
        repository.saveCcalGoal(ccalGoal, context)
        repository.saveStepsGoal(stepsGoal, context)
    }
}