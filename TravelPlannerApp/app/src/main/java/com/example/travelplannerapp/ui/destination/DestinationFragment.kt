package com.example.travelplannerapp.ui.destination

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplannerapp.activities.DetailActivity
import com.example.travelplannerapp.adapters.destination_adapter.DestinationAdapter
import com.example.travelplannerapp.databinding.FragmentDestinationsBinding
import com.example.travelplannerapp.listeners.OnItemClickListener
import com.example.travelplannerapp.model.Destination

class DestinationFragment : Fragment() {

    private lateinit var destinationViewModel: DestinationViewModel
    private lateinit var destinationBinding: FragmentDestinationsBinding
    private lateinit var onItemClickListener: OnItemClickListener
    private lateinit var destinationAdapter: DestinationAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        destinationViewModel =
                ViewModelProvider(this).get(DestinationViewModel::class.java)
        destinationBinding = FragmentDestinationsBinding.inflate(inflater, container, false)
        destinationAdapter = DestinationAdapter(onItemClickListener)
        setupRecyclerView()
        observeDestinationData()
        return destinationBinding.root
    }

    private fun observeDestinationData() {
        destinationViewModel.fetchDestinationData().observe(viewLifecycleOwner, {
            destinationAdapter.setListData(it)
            destinationAdapter.notifyDataSetChanged()
            destinationAdapter.setOnItemClickListener(object : OnItemClickListener {
                override fun onItemClick(destinationList: Destination) {
                    Intent(context, DetailActivity::class.java).apply {
                        putExtra("destinationName", destinationList.destinationName)
                        putExtra("destinationCountry", destinationList.destinationCountry)
                        putExtra("destinationDate", destinationList.destinationDate)
                        putExtra("destinationDescription", destinationList.destinationDescription)
                        putExtra("destinationImage", destinationList.destinationImage)
                        putExtra("destinationImage1", destinationList.destinationImage1)
                        putExtra("destinationImage2", destinationList.destinationImage2)
                        putExtra("destinationImage3", destinationList.destinationImage3)
                        putExtra("destinationLat", destinationList.destinationLat)
                        putExtra("destinationLng", destinationList.destinationLng)
                        putExtra("destinationLocation", destinationList.destinationLocation)
                        putExtra("destinationPrice", destinationList.destinationPrice)
                        startActivity(this)
                    }
                    //Log.e("TAG", destinationList.destinationName!!)
                }

            })
            searchDestinationData()
        })
    }

    private fun searchDestinationData() {
        destinationBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                destinationAdapter.filter.filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                destinationAdapter.filter.filter(newText)
                return true
            }

        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemClickListener) {
            onItemClickListener = context
        }
    }

    private fun setupRecyclerView() {
        destinationBinding.recyclerView.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
        )
        destinationBinding.recyclerView.adapter = destinationAdapter
    }
}