package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.repository.NasaRepository

class RefreshAsteroidListWorker(appContext: Context, params: WorkerParameters): CoroutineWorker(appContext, params) {

    private val database = AsteroidDataBase.getInstance(appContext)
    private val repository = NasaRepository(database)

    companion object {
        const val WORK_NAME = "RefreshAsteroidList"
        const val WORK_ONE_TIME_NAME = "RefreshAsteroidListOnes"
    }

    override suspend fun doWork(): Result {
        return try {
            repository.refreshAsteroidList()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
