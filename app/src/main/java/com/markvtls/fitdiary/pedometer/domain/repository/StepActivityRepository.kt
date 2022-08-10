package com.markvtls.fitdiary.pedometer.domain.repository

import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import kotlinx.coroutines.flow.Flow

interface StepActivityRepository {

    fun getStepsByDate(date: String): Flow<StepActivity>

    fun getLastInsertionDate(): String

    fun getAllForCurrentWeek(): Flow<List<StepActivity>>

    suspend fun update(stepActivity: StepActivity)

    suspend fun insertDailySteps(stepActivity: StepActivity)

    suspend fun deleteByDate(date: String)

    fun sendCommandToService(action: String)

}