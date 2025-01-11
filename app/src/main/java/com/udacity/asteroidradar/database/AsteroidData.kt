package com.udacity.asteroidradar.database

import android.os.Parcelable
import androidx.lifecycle.map
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.Asteroid
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "asteroid_database_table")
data class AsteroidData(
    @PrimaryKey
    var id: Long,

    @ColumnInfo(name = "codename")
    val codename: String = "",

    @ColumnInfo(name = "closeApproachDate")
    var closeApproachDate: String = "",

    @ColumnInfo(name = "absoluteMagnitude")
    var absoluteMagnitude: Double = 0.0,

    @ColumnInfo(name = "estimatedDiameter")
    var estimatedDiameter: Double = 0.0,

    @ColumnInfo(name = "relativeVelocity")
    var relativeVelocity: Double = 0.0,

    @ColumnInfo(name = "distanceFromEarth")
    var distanceFromEarth: Double = 0.0,

    @ColumnInfo(name = "isPotentiallyHazardous")
    var isPotentiallyHazardous: Boolean = false,

    ) : Parcelable


fun List<AsteroidData>.asDomainModel(): List<Asteroid> {
         return map {
             asteroidData ->
            Asteroid(
                id = asteroidData.id,
                codename = asteroidData.codename,
                closeApproachDate = asteroidData.closeApproachDate,
                absoluteMagnitude = asteroidData.absoluteMagnitude,
                estimatedDiameter = asteroidData.estimatedDiameter,
                relativeVelocity = asteroidData.relativeVelocity,
                distanceFromEarth = asteroidData.distanceFromEarth,
                isPotentiallyHazardous = asteroidData.isPotentiallyHazardous,
            )
        }
}

fun List<Asteroid>.asDatabaseModel(): List<AsteroidData>{
    return map {
            asteroid ->
        AsteroidData(
            id = asteroid.id,
            codename = asteroid.codename,
            closeApproachDate = asteroid.closeApproachDate,
            absoluteMagnitude = asteroid.absoluteMagnitude,
            estimatedDiameter = asteroid.estimatedDiameter,
            relativeVelocity = asteroid.relativeVelocity,
            distanceFromEarth = asteroid.distanceFromEarth,
            isPotentiallyHazardous = asteroid.isPotentiallyHazardous,
        )
    }
}
