package com.example.travelplannerapp.ui.plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelplannerapp.adapters.plan_adapter.PlanAdapter
import com.example.travelplannerapp.databinding.FragmentPlanBinding

class PlanFragment : Fragment() {

    private lateinit var planViewModel: PlanViewModel
    private lateinit var planBinding: FragmentPlanBinding
    private lateinit var planAdapter: PlanAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        planViewModel =
                ViewModelProvider(this).get(PlanViewModel::class.java)
        planBinding = FragmentPlanBinding.inflate(inflater, container, false)
        planAdapter = PlanAdapter(requireContext())
        setupRecyclerView()
        observePlanData()
        return planBinding.root

    }

    private fun setupRecyclerView() {
        planBinding.rvPlans.layoutManager = LinearLayoutManager(
                activity,
                LinearLayoutManager.VERTICAL,
                false
        )
        planBinding.rvPlans.adapter = planAdapter
    }

    private fun observePlanData() {
        planViewModel.fetchPlanData().observe(viewLifecycleOwner, {
            planAdapter.setListData(it)
            planAdapter.notifyDataSetChanged()
        })
    }

}