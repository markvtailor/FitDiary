package com.markvtls.fitdiary.profile.data.repository

import android.content.Context
import com.markvtls.fitdiary.profile.data.SettingsDataStore
import com.markvtls.fitdiary.profile.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserProfileRepositoryImpl @Inject constructor(
    private val settings: SettingsDataStore): UserProfileRepository {
    override suspend fun saveStepsGoal(stepsGoal: Int, context: Context) {
       settings.saveStepsGoalToPreferencesStore(stepsGoal, context)
    }

    override suspend fun saveCcalGoal(ccalGoal: Int, context: Context) {
        settings.saveCcalGoalToPreferencesStore(ccalGoal, context)
    }

    override suspend fun savePedometerState(state: Boolean, context: Context) {
        settings.savePedometerStateToPreferencesStore(state, context)
    }

    override fun getCcalGoal(): Flow<Int> {
        return settings.ccalGoalFlow
    }

    override fun getStepsGoal(): Flow<Int> {
        return settings.stepsGoalFlow
    }

    override fun getPedometerState(): Flow<Boolean> {
        return settings.pedometerStateFlow
    }
}