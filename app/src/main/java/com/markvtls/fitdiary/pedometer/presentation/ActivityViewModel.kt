package com.markvtls.fitdiary.pedometer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.fitdiary.pedometer.data.source.local.StepActivity
import com.markvtls.fitdiary.pedometer.domain.model.StepActivityDomain
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetStepsByDateUseCase
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetAllStepsUseCase
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetStepsForLastWeekUseCase
import com.markvtls.fitdiary.profile.domain.model.Preferences
import com.markvtls.fitdiary.profile.domain.use_cases.GetPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val getAll: GetAllStepsUseCase,
    private val getOne: GetStepsByDateUseCase,
    private val getAllForWeek: GetStepsForLastWeekUseCase,
    private val getPreferences: GetPreferencesUseCase
): ViewModel() {

    private var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yMMdd"))
    private lateinit var _currentWeekSteps: Flow<List<StepActivityDomain>>
    val currentWeekSteps get() = _currentWeekSteps
    private lateinit var _currentDaySteps: Flow<StepActivity>
    val currentDaySteps get() = _currentDaySteps

    private var _preferences: Flow<Preferences> = getPreferences()
    val preferences get() = _preferences

    init {
        getAllSteps()
        getCurrentDaySteps(currentDate)
        getStepsForCurrentWeek()
    }


    private fun getCurrentDaySteps(date: String) {
        val actualDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yMMdd"))
        currentDate = actualDate
       _currentDaySteps = getOne(date)
    }

    private fun getAllSteps() {
        _currentWeekSteps = getAll()
    }

    private fun getStepsForCurrentWeek() {
        viewModelScope.launch {
            getAllForWeek().collect {
                 it.forEach {
                     println("${it.date} ${it.steps}")
                 }
            }
        }

    }
}