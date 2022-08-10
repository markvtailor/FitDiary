package com.markvtls.fitdiary.pedometer.presentation

import androidx.lifecycle.ViewModel
import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import com.markvtls.fitdiary.pedometer.domain.model.StepActivityDomain
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetStepsByDateUseCase
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetStepsForCurrentWeekUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getForWeek: GetStepsForCurrentWeekUseCase,
    private val getOne: GetStepsByDateUseCase
): ViewModel() {

    private var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yMMdd"))
    private lateinit var _currentWeekSteps: Flow<List<StepActivityDomain>>
    val currentWeekSteps get() = _currentWeekSteps
    private lateinit var _currentDaySteps: Flow<StepActivity>
    val currentDaySteps get() = _currentDaySteps


    init {
        getCurrentWeekSteps()
        getCurrentDaySteps(currentDate)
    }


    private fun getCurrentDaySteps(date: String) {
        val actualDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yMMdd"))
        currentDate = actualDate
       _currentDaySteps = getOne(date)
    }

    private fun getCurrentWeekSteps() {
        _currentWeekSteps = getForWeek()
    }
}