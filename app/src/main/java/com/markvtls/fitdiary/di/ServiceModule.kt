package com.markvtls.fitdiary.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.markvtls.fitdiary.R
import com.markvtls.fitdiary.MainActivity
import com.markvtls.fitdiary.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent

import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped


@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent(@ApplicationContext app: Context): PendingIntent = PendingIntent
        .getActivity(
            app,
            0,
            Intent(
                app,
                MainActivity::class.java).also {
                it.action = Constants.ACTION_SHOW_USER_ACTIVITY_FRAGMENT
            }, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder(@ApplicationContext app: Context, pendingIntent: PendingIntent) =
        NotificationCompat.Builder(app, Constants.NOTIFICATION_CHANNEL_ID)
        .setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_activity_menu_option)
        .setContentTitle("FitDiary - Step Counter")
        .setContentText("0")
        .setContentIntent(pendingIntent)


}