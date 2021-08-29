package com.example.travelplannerapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.travelplannerapp.databinding.ActivityDetailBinding
import com.example.travelplannerapp.fragments.DetailFragment
import com.example.travelplannerapp.fragments.MapsFragment
import com.example.travelplannerapp.fragments.adapters.ViewPagerAdapter

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setUpPager()
        setContentView(detailBinding.root)
    }

    private fun setUpPager() {
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPagerAdapter.addFragment(DetailFragment(), "Details")
        viewPagerAdapter.addFragment(MapsFragment(), "Google Map")
        detailBinding.viewPager.adapter = viewPagerAdapter
        detailBinding.tabLayout.setupWithViewPager(detailBinding.viewPager)
    }
}