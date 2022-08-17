package com.markvtls.fitdiary

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.markvtls.fitdiary.food.data.source.local.FoodServingDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitDiaryApplication : Application() {

    val database: FoodServingDatabase by lazy { FoodServingDatabase.getDatabase(this) }
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val descriptionText = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        const val CHANNEL_ID = "activity_id"
    }

}