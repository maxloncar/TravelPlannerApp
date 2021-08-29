package com.example.travelplannerapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.travelplannerapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference? = null
    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://travelplannerapp-e5c14-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database?.reference!!.child("Profiles")

        binding.bSignUp.setOnClickListener { signUp() }
        binding.tvLogin.setOnClickListener { onBackPressed() }
        setContentView(binding.root)
    }

    private fun signUp() {
        when {
            TextUtils.isEmpty(binding.etRegisterName.text.toString()) -> {
                Toast.makeText(this, "Please enter your name.", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(binding.etRegisterEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, "Please enter your e-mail address.", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(binding.etRegisterPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val email: String = binding.etRegisterEmail.text.toString().trim { it <= ' ' }
                val name: String = binding.etRegisterName.text.toString()
                val password: String = binding.etRegisterPassword.text.toString().trim { it <= ' ' }

                // Firebase authentication - registration of a user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                // Sign up success, update UI with the signed-in user's information
                                val currentUser = auth.currentUser
                                val currentUserDatabase = databaseReference?.child((currentUser?.uid!!))
                                currentUserDatabase?.child("name")?.setValue(name)
                                Log.i("NAME", name)
                                startActivity(Intent(this, LoginActivity::class.java))
                                Toast.makeText(this, "You have successfully registered.", Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                // If registration fails, display a message to the user.
                                Toast.makeText(this, "Registration failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }
    }
}