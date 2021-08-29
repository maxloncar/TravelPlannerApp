package com.example.travelplannerapp.model

data class Plan(
        var destinationName: String? = null,
        var destinationImage: String? = null,
        var planDate: String? = null,
        var planNumberOfTravelers: Int? = null,
        var planPrice: Int? = null,
        var planNote: String? = null
)
