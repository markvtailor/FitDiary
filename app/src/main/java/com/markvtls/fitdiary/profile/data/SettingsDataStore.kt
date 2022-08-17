package com.markvtls.fitdiary.profile.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private const val SETTINGS = "user_settings"
private val IS_PEDOMETER_ON = booleanPreferencesKey("is_pedometer_on")
private val STEPS_GOAL = intPreferencesKey("steps_goal")
private val CCAL_GOAL = intPreferencesKey("ccal_goal")


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SETTINGS
)

class SettingsDataStore(context: Context) {


    suspend fun savePedometerStateToPreferencesStore(isPedometerOn: Boolean, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[IS_PEDOMETER_ON] = isPedometerOn
        }
    }

    suspend fun saveStepsGoalToPreferencesStore(stepsGoal: Int, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[STEPS_GOAL] = stepsGoal
        }
    }

    suspend fun saveCcalGoalToPreferencesStore(ccalGoal: Int, context: Context) {
        context.dataStore.edit { preferences ->
            preferences[CCAL_GOAL] = ccalGoal
        }
    }

    val pedometerStateFlow: Flow<Boolean> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_PEDOMETER_ON] ?: true
        }

    val stepsGoalFlow: Flow<Int> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[STEPS_GOAL] ?: 10000
        }

    val ccalGoalFlow: Flow<Int> = context.dataStore.data
        .catch {
            if (it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[CCAL_GOAL] ?: 2000
        }
}