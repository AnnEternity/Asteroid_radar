package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.repository.NasaRepository
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

enum class AsteroidFilter { ALL, WEEK, TODAY }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val nasaRepository = NasaRepository(AsteroidDataBase.getInstance(application))

    private val asteroidFilterStatus = MutableLiveData<AsteroidFilter>(AsteroidFilter.ALL)

    val asteroidList: LiveData<List<Asteroid>> = asteroidFilterStatus.switchMap {
        when (it) {
            AsteroidFilter.ALL -> nasaRepository.asteroids
            AsteroidFilter.WEEK -> nasaRepository.weekAsteroid
            AsteroidFilter.TODAY -> nasaRepository.todayAsteroid
        }
    }

    private val _pictureOfTheDay = nasaRepository.pictureOfTheDay
    val pictureOfTheDay: LiveData<PictureOfDay?>
        get() = _pictureOfTheDay

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid?>()
    val navigateToSelectedAsteroid: LiveData<Asteroid?>
        get() = _navigateToSelectedAsteroid

    fun asteroidOnClick (asteroid: Asteroid) {
       _navigateToSelectedAsteroid.value = asteroid
    }

    fun asteroidFilterOnClick (asteroidFilter: AsteroidFilter){
        asteroidFilterStatus.value = asteroidFilter
    }

    fun asteroidOnClicked (){
        _navigateToSelectedAsteroid.value = null
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }


}