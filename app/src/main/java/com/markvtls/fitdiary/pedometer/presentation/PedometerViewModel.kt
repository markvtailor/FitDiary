package com.markvtls.fitdiary.pedometer.presentation

import androidx.lifecycle.ViewModel
import com.markvtls.fitdiary.pedometer.domain.model.StepActivityDomain
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetAllStepsUseCase
import com.markvtls.fitdiary.profile.domain.model.Preferences
import com.markvtls.fitdiary.profile.domain.use_cases.GetPreferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PedometerViewModel @Inject constructor(
    private val getAll: GetAllStepsUseCase,
    getPreferences: GetPreferencesUseCase
) : ViewModel() {

    private lateinit var _currentWeekSteps: Flow<List<StepActivityDomain>>
    val currentWeekSteps get() = _currentWeekSteps

    private var _preferences: Flow<Preferences> = getPreferences()
    val preferences get() = _preferences

    init {
        getAllSteps()
    }

    private fun getAllSteps() {
        _currentWeekSteps = getAll()
    }

}