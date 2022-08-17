package com.markvtls.fitdiary.profile.presentation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.markvtls.fitdiary.profile.domain.model.DayOverview
import com.markvtls.fitdiary.profile.domain.model.Preferences
import com.markvtls.fitdiary.profile.domain.model.Settings
import com.markvtls.fitdiary.profile.domain.use_cases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    private val getPreferences: GetPreferencesUseCase,
    private val saveSettings: SaveSettingsUseCase,
    private val savePreferences: SavePreferencesUseCase,
    private val getOverview: GetOverviewUseCase
) : ViewModel() {


    private lateinit var _settings: Flow<Settings>
    private lateinit var _preferences: Flow<Preferences>


    val weekOverview = MutableLiveData<List<DayOverview>>(emptyList())
    val preferences get() = _preferences
    val settings get() = _settings


    init {
        viewModelScope.launch {
            getOverviewInfo()
            _settings = getSettings()
            _preferences = getPreferences()
        }
    }

    fun saveNewSettings(pedometerState: Boolean, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            saveSettings(pedometerState, context)
        }
    }

    fun saveNewPreferences(ccalGoal: Int, stepsGoal: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            savePreferences(ccalGoal, stepsGoal, context)
        }
    }

    private fun getOverviewInfo() {
        viewModelScope.launch {
            getOverview().collect {
                weekOverview.postValue(it)
            }
        }
    }
}