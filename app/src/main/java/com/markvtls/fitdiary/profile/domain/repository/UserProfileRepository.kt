package com.markvtls.fitdiary.profile.domain.repository

import android.content.Context
import kotlinx.coroutines.flow.Flow

interface UserProfileRepository {

    suspend fun saveStepsGoal(stepsGoal: Int, context: Context)

    suspend fun saveCcalGoal(ccalGoal: Int, context: Context)

    suspend fun savePedometerState(state: Boolean, context: Context)

    fun getCcalGoal(): Flow<Int>

    fun getStepsGoal(): Flow<Int>

    fun getPedometerState(): Flow<Boolean>

}