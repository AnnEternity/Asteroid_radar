package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.database.asDatabaseModel
import com.udacity.asteroidradar.database.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


class NasaRepository(private val database: AsteroidDataBase) {

    val asteroids: LiveData<List<Asteroid>> =
        database.AsteroidDatabaseDao.getAll(
            DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ZonedDateTime.now())
        ).map {
            it.asDomainModel()
        }

    val pictureOfTheDay: LiveData<PictureOfDay?> =
        database.AsteroidDatabaseDao.getPicture().map { it?.asDomainModel() }

    suspend fun refreshAsteroidList() {
        withContext(Dispatchers.IO) {
            val asteroidList = AsteroidApi.retrofitService.getAsteroidList()
            val json = JSONObject(asteroidList)
            val parsed = parseAsteroidsJsonResult(json)
            database.AsteroidDatabaseDao.insertAll(parsed.asDatabaseModel())
            database.AsteroidDatabaseDao.deleteUpUntil(DateTimeFormatter.ofPattern("yyyy-MM-dd").format(ZonedDateTime.now()))
        }
    }

    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            val picture = AsteroidApi.retrofitService.getPhoto()
            database.AsteroidDatabaseDao.insertPictureOfTheDay(picture.asDatabaseModel())
        }
    }
}
