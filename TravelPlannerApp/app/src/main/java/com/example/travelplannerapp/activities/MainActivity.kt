package com.example.travelplannerapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.travelplannerapp.R
import com.example.travelplannerapp.databinding.ActivityMainBinding
import com.example.travelplannerapp.listeners.OnItemClickListener
import com.example.travelplannerapp.model.Destination
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://travelplannerapp-e5c14-default-rtdb.europe-west1.firebasedatabase.app/")
        databaseReference = database.reference.child("Profiles")

        loadDataIntoNavHeader()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
                setOf(
                        R.id.nav_destinations, R.id.nav_plans
                ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    private fun loadDataIntoNavHeader() {
        val currentUser = auth.currentUser
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val navUserName = headerView.findViewById<TextView>(R.id.tv_userName)
        val navUserEmail = headerView.findViewById<TextView>(R.id.tv_userEmail)
        val userReference = databaseReference.child(currentUser?.uid!!)

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                navUserName.text = snapshot.child("name").value.toString()
                navUserEmail.text = currentUser.email
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("TAG", "No data found.")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val logout = menu.findItem(R.id.action_logout)
        logout.setOnMenuItemClickListener { logout() }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onItemClick(destinationList: Destination) {
        supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .commit()
    }

    private fun logout(): Boolean {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
        return true
    }
}