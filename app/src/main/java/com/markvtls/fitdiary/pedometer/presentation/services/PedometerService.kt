package com.markvtls.fitdiary.pedometer.presentation.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_LOW
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.MutableLiveData
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetLastInsertionDateUseCase
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetStepsByDateUseCase
import com.markvtls.fitdiary.pedometer.domain.use_cases.InsertDailyStepsUseCase
import com.markvtls.fitdiary.utils.Constants.ACTION_PAUSE_SERVICE
import com.markvtls.fitdiary.utils.Constants.ACTION_START_OR_RESUME_SERVICE
import com.markvtls.fitdiary.utils.Constants.ACTION_STOP_SERVICE
import com.markvtls.fitdiary.utils.Constants.NOTIFICATION_CHANNEL_ID
import com.markvtls.fitdiary.utils.Constants.NOTIFICATION_CHANNEL_NAME
import com.markvtls.fitdiary.utils.Constants.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject


@AndroidEntryPoint
class PedometerService : LifecycleService(), SensorEventListener {

    @Inject
    lateinit var getStepsByDateUseCase: GetStepsByDateUseCase

    @Inject
    lateinit var insertDailyStepsUseCase: InsertDailyStepsUseCase

    @Inject
    lateinit var lastInsertionDateUseCase: GetLastInsertionDateUseCase

    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    private lateinit var currentNotificationBuilder: NotificationCompat.Builder

    private var currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yMMdd"))
    private var currentSteps: Int = 0
    private var isFirstRun = true
    private var sensorManager: SensorManager? = null

    companion object {
        val counter = MutableLiveData(0)
    }

    private fun getInitialValue() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                getStepsByDateUseCase(currentDate).collect {
                    currentSteps = it.steps
                }
            } catch (e: Exception) {
                currentSteps = 0
            }

        }

    }

    override fun onCreate() {

        super.onCreate()
        currentNotificationBuilder = notificationBuilder
        getInitialValue()
        sensorManager = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        checkSensorAvailability()
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager?.unregisterListener(this)
    }

    private fun checkSensorAvailability() {

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "На устройстве отсутствует сенсор шагов", Toast.LENGTH_SHORT)
                .show()
        } else {
            sensorManager?.registerListener(
                this,
                stepSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            ) //sensor data acquisition rate
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startService()
                        isFirstRun = false
                    } else {
                        Log.i("Service", "Service resumed")
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Log.i("Service", "Service paused")
                }
                ACTION_STOP_SERVICE -> {
                    Log.i("Service", "Service stopped")
                }
                else -> Log.i("Service", "No such action")
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startService() {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        startForeground(NOTIFICATION_ID, notificationBuilder.build())

        counter.observe(this) {
            val notification = currentNotificationBuilder.setContentText(it.toString())
            notificationManager.notify(NOTIFICATION_ID, notification.build())
            saveSteps()
        }
    }

    private fun saveSteps() {
        CoroutineScope(Dispatchers.IO).launch {
            checkCurrentDate()
            insertDailyStepsUseCase(currentDate, currentSteps)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) {
            currentSteps++
            counter.postValue(currentSteps)
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        println("Accuracy changed")
    }

    private fun checkCurrentDate() {
        CoroutineScope(Dispatchers.IO).launch {
            val lastInsertionDate = lastInsertionDateUseCase()

            val actualDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yMMdd"))
            println(lastInsertionDate)
            println(actualDate)
            if (lastInsertionDate != actualDate) {
                println("nullify")
                currentSteps = 0
                currentDate = actualDate
                counter.postValue(currentSteps)

            }
        }
    }
}
