package com.markvtls.fitdiary.pedometer.domain.repository

import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import kotlinx.coroutines.flow.Flow

interface PedometerRepository {

    fun getStepsByDate(date: String): Flow<StepActivity>

    fun getLastInsertionDate(): String

    fun getAll(): Flow<List<StepActivity>>

    fun getAllForCurrentWeek(): Flow<List<StepActivity>>

    suspend fun update(stepActivity: StepActivity)

    suspend fun insertDailySteps(stepActivity: StepActivity)

    suspend fun deleteByDate(date: String)

}