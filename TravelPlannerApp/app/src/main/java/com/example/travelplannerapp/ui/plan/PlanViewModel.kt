package com.example.travelplannerapp.ui.plan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.travelplannerapp.model.Plan
import com.example.travelplannerapp.repositories.PlanRepository

class PlanViewModel : ViewModel() {

    private val planRepository = PlanRepository()

    fun fetchPlanData(): LiveData<MutableList<Plan>> {
        val mutableData = MutableLiveData<MutableList<Plan>>()
        planRepository.getPlanData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}