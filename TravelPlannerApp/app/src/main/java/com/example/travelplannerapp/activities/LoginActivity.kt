package com.example.travelplannerapp.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.travelplannerapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        checkIfUserIsLoggedIn()
        binding.bLogin.setOnClickListener { login() }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        setContentView(binding.root)
    }

    private fun checkIfUserIsLoggedIn() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun login() {
        when {
            TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, "Please enter your e-mail address.", Toast.LENGTH_SHORT).show()
            }

            TextUtils.isEmpty(binding.etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                Toast.makeText(this, "Please enter your password.", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val email: String = binding.etLoginEmail.text.toString().trim { it <= ' ' }
                val password: String = binding.etLoginPassword.text.toString().trim { it <= ' ' }

                // Firebase authentication - login of a user
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
                            } else {
                                // If login fails
                                Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        }

    }
}