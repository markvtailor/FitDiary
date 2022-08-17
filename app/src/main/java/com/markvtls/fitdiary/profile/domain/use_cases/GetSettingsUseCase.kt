package com.markvtls.fitdiary.profile.domain.use_cases

import com.markvtls.fitdiary.profile.domain.model.Settings
import com.markvtls.fitdiary.profile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    operator fun invoke(): Flow<Settings> = flow {
        repository.getPedometerState().collect {
            val pedometerState = it
            val settings = Settings(pedometerState)
            emit(settings)
        }
    }
}