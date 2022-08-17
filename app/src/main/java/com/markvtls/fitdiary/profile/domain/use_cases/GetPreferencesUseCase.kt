package com.markvtls.fitdiary.profile.domain.use_cases

import com.markvtls.fitdiary.profile.domain.model.Preferences
import com.markvtls.fitdiary.profile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPreferencesUseCase  @Inject constructor(
    private val repository: UserProfileRepository
) {
    operator fun invoke(): Flow<Preferences> = flow {
        repository.getCcalGoal().collect { ccal ->
            repository.getStepsGoal().collect { steps ->
                val preferences = Preferences(steps, ccal)
                emit(preferences)
            }
        }
    }
}