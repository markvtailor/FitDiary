package com.markvtls.fitdiary

import android.app.Application
import com.markvtls.fitdiary.data.FoodServingDatabase

class FoodApplication: Application() {
    val database: FoodServingDatabase by lazy { FoodServingDatabase.getDatabase(this) }
}