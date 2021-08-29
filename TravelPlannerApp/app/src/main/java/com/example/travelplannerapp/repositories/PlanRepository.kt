package com.example.travelplannerapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.travelplannerapp.model.Plan
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PlanRepository {

    fun getPlanData(): LiveData<MutableList<Plan>> {
        val mutableData = MutableLiveData<MutableList<Plan>>()
        FirebaseDatabase.getInstance("https://travelplannerapp-e5c14-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference.child("Profiles").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Plans")
                .addValueEventListener(object : ValueEventListener {
                    val plans = mutableListOf<Plan>()
                    override fun onDataChange(snapshot: DataSnapshot) {
                        plans.clear()
                        if (snapshot.exists()) {
                            for (planSnapshot in snapshot.children) {
                                val plan = planSnapshot.getValue(Plan::class.java)
                                plans.add(plan!!)
                            }
                            mutableData.value = plans
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("Failed to read value.", error.toException())
                    }

                })
        return mutableData

    }

    fun updatePlan(destinationName: String, mutableMap: MutableMap<String, Any>): Task<Void> {
        return FirebaseDatabase.getInstance("https://travelplannerapp-e5c14-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference.child("Profiles").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Plans")
                .child(destinationName).updateChildren(mutableMap)
    }

    fun removePlan(destinationName: String): Task<Void> {
        return FirebaseDatabase.getInstance("https://travelplannerapp-e5c14-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference.child("Profiles").child(FirebaseAuth.getInstance().currentUser!!.uid).child("Plans")
                .child(destinationName).removeValue()
    }
}