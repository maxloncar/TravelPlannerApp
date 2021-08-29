package com.example.travelplannerapp.listeners

import com.example.travelplannerapp.model.Destination

interface OnItemClickListener {
    fun onItemClick(destinationList: Destination)
}