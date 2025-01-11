package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.Asteroid

@Database(entities = [AsteroidData::class, PictureOfTheDayEntity::class], version = 2, exportSchema = false)
abstract class AsteroidDataBase : RoomDatabase() {
    abstract val AsteroidDatabaseDao: AsteroidDataBaseDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDataBase? = null

        fun getInstance(context: Context): AsteroidDataBase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDataBase::class.java,
                        "sleep_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}