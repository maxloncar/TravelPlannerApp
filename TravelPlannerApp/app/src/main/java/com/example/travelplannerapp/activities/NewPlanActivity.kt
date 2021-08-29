package com.example.travelplannerapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelplannerapp.R
import com.example.travelplannerapp.databinding.ActivityNewPlanBinding
import com.example.travelplannerapp.model.Plan
import com.example.travelplannerapp.repositories.PlanRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class NewPlanActivity : AppCompatActivity() {

    private lateinit var newPlanBinding: ActivityNewPlanBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var planRepository: PlanRepository
    var destinationName: String? = null
    var destinationImage: String? = null
    private var destinationPrice: Int? = null
    private var editPlanDate: String? = null
    private var editPlanNumberOfTravelers: Int? = null
    private var editPlanNote: String? = null
    private var editPlanPrice: Int? = null
    private var editDestinationName: String? = null
    private var editDestinationImage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newPlanBinding = ActivityNewPlanBinding.inflate(layoutInflater)

        planRepository = PlanRepository()

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance("https://travelplannerapp-e5c14-default-rtdb.europe-west1.firebasedatabase.app/")
                .reference.child("Profiles")

        //  Data from DetailFragment
        val bundle: Bundle? = intent.extras
        destinationName = bundle!!.getString("planDestinationName")
        destinationImage = bundle.getString("planDestinationImage")
        destinationPrice = bundle.getInt("planDestinationPrice")
        newPlanBinding.tvPlanDestinationName.text = destinationName
        Picasso.get().load(destinationImage).into(newPlanBinding.ivPlanDestinationImage)

        //  Data from PlanAdapter (data for update/edit)
        editPlanDate = bundle.getString("editPlanDate")
        editPlanNumberOfTravelers = bundle.getInt("editPlanNumberOfTravelers")
        editPlanNote = bundle.getString("editPlanNote")
        editPlanPrice = bundle.getInt("editPlanPrice")
        editDestinationName = bundle.getString("editDestinationName")
        editDestinationImage = bundle.getString("editDestinationImage")

        editPlan()

        newPlanBinding.bAddPlan.setOnClickListener { addPlan() }
        newPlanBinding.bCancelPlan.setOnClickListener { onBackPressed() }

        setContentView(newPlanBinding.root)
    }

    private fun editPlan() {
        if (editPlanDate != null && editPlanNumberOfTravelers != null) {
            newPlanBinding.bAddPlan.text = getString(R.string.updatePlan)
            newPlanBinding.etPlanDate.setText(editPlanDate)
            newPlanBinding.etPlanNumberOfTravelers.setText(editPlanNumberOfTravelers.toString())
            newPlanBinding.etPlanNote.setText(editPlanNote)
            newPlanBinding.tvPlanDestinationName.text = editDestinationName
            Picasso.get().load(editDestinationImage).into(newPlanBinding.ivPlanDestinationImage)

        } else {
            newPlanBinding.bAddPlan.text = getString(R.string.addPlan)
        }
    }

    private fun addPlan() {
        if (editPlanDate == null || editPlanNumberOfTravelers == null) {
            when {
                TextUtils.isEmpty(newPlanBinding.etPlanDate.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter the desired date range.", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(newPlanBinding.etPlanNumberOfTravelers.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter the number of travelers.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val planDate = newPlanBinding.etPlanDate.text.toString()
                    val numberOfTravelers = newPlanBinding.etPlanNumberOfTravelers.text.toString().toInt()
                    val planPrice = destinationPrice!! * numberOfTravelers
                    val planNote = newPlanBinding.etPlanNote.text.toString()

                    val currentUser = auth.currentUser
                    val plan = Plan(destinationName, destinationImage, planDate, numberOfTravelers, planPrice, planNote)
                    databaseReference.child(currentUser!!.uid).child("Plans").child(destinationName!!).setValue(plan)
                            .addOnSuccessListener {
                                startActivity(Intent(this, MainActivity::class.java))
                                Toast.makeText(this, "You have successfully saved the plan for $destinationName travel.", Toast.LENGTH_SHORT).show()
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                            }
                }
            }
        } else {
            val mutableMap = mutableMapOf<String, Any>()
            mutableMap["planDate"] = newPlanBinding.etPlanDate.text.toString()
            mutableMap["planNumberOfTravelers"] = newPlanBinding.etPlanNumberOfTravelers.text.toString().toInt()
            mutableMap["planNote"] = newPlanBinding.etPlanNote.text.toString()
            mutableMap["planPrice"] = (editPlanPrice.toString().toInt() / editPlanNumberOfTravelers.toString().toInt()) * newPlanBinding.etPlanNumberOfTravelers.text.toString().toInt()
            planRepository.updatePlan(editDestinationName!!, mutableMap).addOnSuccessListener {
                Toast.makeText(this, "Plan for $editDestinationName is updated.", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}