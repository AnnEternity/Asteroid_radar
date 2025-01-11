package com.udacity.asteroidradar.work

import android.app.Application
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class NasaApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val asteroidListRepeatingRequest =
            PeriodicWorkRequestBuilder<RefreshAsteroidListWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshAsteroidListWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            asteroidListRepeatingRequest)

        val pictureOfTheDayRepeatingRequest =
            PeriodicWorkRequestBuilder<RefreshPictureOfTheDayWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshPictureOfTheDayWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            pictureOfTheDayRepeatingRequest)


        //I added oneTimeRequest because periodic work doesn't start immediately when app is launching
        val pictureOneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshAsteroidListWorker>().build()

        WorkManager.getInstance().enqueueUniqueWork(
            RefreshPictureOfTheDayWorker.WORK_ONE_TIME_NAME,
            ExistingWorkPolicy.KEEP,
            pictureOneTimeWorkRequest)

        val asteroidOneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshPictureOfTheDayWorker>().build()

        WorkManager.getInstance().enqueueUniqueWork(
            RefreshAsteroidListWorker.WORK_ONE_TIME_NAME,
            ExistingWorkPolicy.KEEP,
            asteroidOneTimeWorkRequest)
    }



}