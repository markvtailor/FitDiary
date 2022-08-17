package com.markvtls.fitdiary.profile.domain.use_cases

import android.content.Context
import com.markvtls.fitdiary.profile.domain.repository.UserProfileRepository
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(pedometerState: Boolean, context: Context) {
        repository.savePedometerState(pedometerState, context)
    }
}