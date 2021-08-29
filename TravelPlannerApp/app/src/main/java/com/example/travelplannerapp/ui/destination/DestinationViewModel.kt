package com.example.travelplannerapp.ui.destination

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelplannerapp.model.Destination
import com.example.travelplannerapp.repositories.DestinationRepository

class DestinationViewModel : ViewModel() {

    private val destinationRepository = DestinationRepository()

    fun fetchDestinationData(): LiveData<MutableList<Destination>> {
        val mutableData = MutableLiveData<MutableList<Destination>>()
        destinationRepository.getDestinationData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}