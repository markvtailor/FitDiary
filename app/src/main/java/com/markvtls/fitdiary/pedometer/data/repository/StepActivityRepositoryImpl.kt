package com.markvtls.fitdiary.pedometer.data.repository

import android.content.Context
import android.content.Intent
import com.markvtls.fitdiary.pedometer.data.source.local.DateHolder
import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import com.markvtls.fitdiary.pedometer.data.source.local.StepActivityDatabase
import com.markvtls.fitdiary.pedometer.domain.repository.StepActivityRepository
import com.markvtls.fitdiary.pedometer.presentation.services.StepActivityService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class StepActivityRepositoryImpl@Inject constructor(
    private val appContext: Context,
    private val database: StepActivityDatabase
)
    : StepActivityRepository {
    override fun getStepsByDate(date: String): Flow<StepActivity> {
        return database.stepActivityDao().getByDate(date)
    }

    override fun getLastInsertionDate(): String {
       return database.stepActivityDao().getLastInsertionDate()
    }

    override fun getAllForCurrentWeek(): Flow<List<StepActivity>> {
        return database.stepActivityDao().getAllForCurrentWeek()
    }

    override suspend fun update(stepActivity: StepActivity) {
        database.stepActivityDao().update(stepActivity)
    }

    override suspend fun insertDailySteps(stepActivity: StepActivity) {
        database.stepActivityDao().insert(stepActivity)
    }

    override suspend fun deleteByDate(date: String) {
        database.stepActivityDao().deleteByDate(DateHolder(date))
    }

    override fun sendCommandToService(action: String) {
        Intent( appContext, StepActivityService::class.java).also {
            it.action = action
            appContext.startService(it)
        }
    }
}