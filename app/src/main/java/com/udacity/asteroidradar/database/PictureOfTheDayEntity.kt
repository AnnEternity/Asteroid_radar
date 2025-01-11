package com.udacity.asteroidradar.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.PictureOfDay
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "picture_of_the_day_table")
data class PictureOfTheDayEntity(
    @PrimaryKey
    var id: Long = 1,

    @ColumnInfo(name = "mediaType")
    val mediaType: String = "",

    @ColumnInfo(name = "title")
    val title: String = "",

    @ColumnInfo(name = "url")
    val url: String = ""

) : Parcelable

fun PictureOfTheDayEntity.asDomainModel(): PictureOfDay{
    return PictureOfDay(mediaType = this.mediaType, title = this.title, url = this.url)
}

fun PictureOfDay.asDatabaseModel(): PictureOfTheDayEntity{
    return PictureOfTheDayEntity(mediaType = this.mediaType, title = this.title, url = this.url)
}
