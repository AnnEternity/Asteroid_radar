package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid

@Dao
interface AsteroidDataBaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroidList: List<AsteroidData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfTheDay(pictureOfTheDayEntity: PictureOfTheDayEntity)

    @Query("SELECT * from picture_of_the_day_table LIMIT 1 ")
    fun getPicture(): LiveData<PictureOfTheDayEntity?>

    @Query("SELECT * from asteroid_database_table WHERE closeApproachDate >= :startingFrom ORDER by closeApproachDate DESC")
    fun getAll(startingFrom: String): LiveData<List<AsteroidData>>

    @Query("SELECT * from asteroid_database_table WHERE closeApproachDate = :startingFrom ORDER by closeApproachDate DESC")
    fun getTodayAsteroids(startingFrom: String): LiveData<List<AsteroidData>>

    @Query("SELECT * from asteroid_database_table WHERE closeApproachDate > :startingFrom ORDER by closeApproachDate DESC")
    fun getWeekAsteroids(startingFrom: String): LiveData<List<AsteroidData>>

    @Query("DELETE FROM ASTEROID_DATABASE_TABLE WHERE closeApproachDate < :upUntil ")
    suspend fun deleteUpUntil(upUntil: String)
}