package com.markvtls.fitdiary

import android.app.Application
import com.markvtls.fitdiary.data.local.FoodServingDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FoodApplication: Application() {
    val database: FoodServingDatabase by lazy { FoodServingDatabase.getDatabase(this) }
}