package com.markvtls.fitdiary.pedometer.data.source.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PedometerDao {

    @Query("SELECT max(date) from stepactivity")
    fun getLastInsertionDate(): String

    @Query("SELECT * from stepactivity WHERE date = :date")
    fun getByDate(date: String): Flow<StepActivity>

    @Query("SELECT * from stepactivity")
    fun getAll(): Flow<List<StepActivity>>

    @Query("SELECT * from stepactivity ORDER BY date desc LIMIT 7")
    fun getAllForCurrentWeek(): Flow<List<StepActivity>>

    @Update
    suspend fun update(stepActivity: StepActivity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stepActivity: StepActivity)

    @Delete(entity = StepActivity::class)
    suspend fun deleteByDate(vararg date: DateHolder)

}