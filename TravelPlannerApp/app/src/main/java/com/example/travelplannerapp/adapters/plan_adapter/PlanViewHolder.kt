package com.example.travelplannerapp.adapters.plan_adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplannerapp.R
import com.example.travelplannerapp.databinding.PlanRowItemBinding
import com.example.travelplannerapp.model.Plan
import com.squareup.picasso.Picasso

class PlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val planRowItemBinding = PlanRowItemBinding.bind(itemView)

    fun bind(plan: Plan) {
        """Planned destination to ${plan.destinationName}""".also { planRowItemBinding.tvPlanName.text = it }
        planRowItemBinding.tvPlanDate.text = plan.planDate
        """€${plan.planPrice} (per ${plan.planNumberOfTravelers} person/s, daily)""".also { planRowItemBinding.tvPlanPrice.text = it }
        planRowItemBinding.tvPlanNote.text = plan.planNote
        Picasso.get()
                .load(plan.destinationImage)
                .fit()
                .placeholder(R.drawable.caribbean)
                .into(planRowItemBinding.ivPlanImage)
    }

    val option = planRowItemBinding.ivOption
}