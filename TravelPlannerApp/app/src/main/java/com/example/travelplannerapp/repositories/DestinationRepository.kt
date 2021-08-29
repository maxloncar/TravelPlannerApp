package com.example.travelplannerapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelplannerapp.model.Destination
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DestinationRepository {

    fun getDestinationData(): LiveData<MutableList<Destination>> {
        val mutableData = MutableLiveData<MutableList<Destination>>()
        FirebaseDatabase.getInstance("https://travelplannerapp-e5c14-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference.child("Destinations")
                .addValueEventListener(object : ValueEventListener {
                    val destinations = mutableListOf<Destination>()
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (destinationSnapshot in snapshot.children) {
                                val destination = destinationSnapshot.getValue(Destination::class.java)
                                destinations.add(destination!!)
                            }
                            mutableData.value = destinations
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("Failed to read value.", error.toException())
                    }

                })
        return mutableData
    }
}