package com.location.codechallenge.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.location.codechallenge.repository.MapRepository
import com.location.codechallenge.repository.models.Site
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MapsViewModel @ViewModelInject constructor(
    private val mapRepository: MapRepository
) : ViewModel() {

    private var allLocations: MutableLiveData<List<Site>> = MutableLiveData()

    init {
        GlobalScope.launch(Dispatchers.IO) {
            mapRepository.getAllLocations().run {
                onFailure { Log.e("ERROR", "Error: ${it.message}") }
                onSuccess { allLocations.postValue(it) }
            }
        }
    }

    fun getAllLocations(): LiveData<List<Site>> = allLocations
}
