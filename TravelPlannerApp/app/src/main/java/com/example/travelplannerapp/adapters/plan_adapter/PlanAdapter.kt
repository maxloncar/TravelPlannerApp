package com.example.travelplannerapp.adapters.plan_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplannerapp.R
import com.example.travelplannerapp.activities.NewPlanActivity
import com.example.travelplannerapp.model.Plan
import com.example.travelplannerapp.repositories.PlanRepository

class PlanAdapter(val context: Context) : RecyclerView.Adapter<PlanViewHolder>() {

    private var planList = mutableListOf<Plan>()
    private lateinit var planRepository: PlanRepository

    fun setListData(data: MutableList<Plan>) {
        planList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.plan_row_item, parent, false)
        planRepository = PlanRepository()
        return PlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val currentPlan = planList[position]
        holder.bind(currentPlan)
        holder.option.setOnClickListener { popUp(holder, position) }
    }

    private fun popUp(holder: PlanViewHolder, position: Int) {
        val popupMenus = PopupMenu(context, holder.option)
        popupMenus.inflate(R.menu.option_menu)
        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_edit -> {
                    Intent(context, NewPlanActivity::class.java).apply {
                        putExtra("editPlanDate", planList[position].planDate)
                        putExtra("editPlanNumberOfTravelers", planList[position].planNumberOfTravelers)
                        putExtra("editPlanNote", planList[position].planNote)
                        putExtra("editPlanPrice", planList[position].planPrice)
                        putExtra("editDestinationName", planList[position].destinationName)
                        putExtra("editDestinationImage", planList[position].destinationImage)
                        context.startActivity(this)
                    }
                    true
                }
                R.id.menu_remove -> {
                    val removedDestinationName = planList[position].destinationName
                    planRepository.removePlan(removedDestinationName!!).addOnSuccessListener {
                        Toast.makeText(context, "Plan for $removedDestinationName is successfully removed.", Toast.LENGTH_SHORT).show()
                        notifyItemRemoved(position)
                    }.addOnFailureListener { error ->
                        Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                else -> false
            }
        }
        popupMenus.show()
    }

    override fun getItemCount(): Int {
        return planList.size
    }
}