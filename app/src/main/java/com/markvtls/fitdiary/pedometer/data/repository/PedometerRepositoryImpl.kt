package com.markvtls.fitdiary.pedometer.data.repository

import com.markvtls.fitdiary.pedometer.data.source.local.DateHolder
import com.markvtls.fitdiary.pedometer.data.source.local.PedometerDatabase
import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import com.markvtls.fitdiary.pedometer.domain.repository.PedometerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PedometerRepositoryImpl @Inject constructor(
    private val database: PedometerDatabase
) : PedometerRepository {
    override fun getStepsByDate(date: String): Flow<StepActivity> {
        return database.stepActivityDao().getByDate(date)
    }

    override fun getLastInsertionDate(): String {
        return database.stepActivityDao().getLastInsertionDate()
    }

    override fun getAll(): Flow<List<StepActivity>> {
        return database.stepActivityDao().getAll()
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

}