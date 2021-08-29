package com.example.travelplannerapp.adapters.destination_adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplannerapp.R
import com.example.travelplannerapp.databinding.DestinationRowItemBinding
import com.example.travelplannerapp.model.Destination
import com.squareup.picasso.Picasso

class DestinationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(destination: Destination) {
        val destinationRowItemBinding = DestinationRowItemBinding.bind(itemView)
        destinationRowItemBinding.tvDestinationName.text = destination.destinationName
        destinationRowItemBinding.tvDestinationCountry.text = destination.destinationCountry
        """€${destination.destinationPrice} (per person, daily)""".also { destinationRowItemBinding.tvDestinationPrice.text = it }
        Picasso.get()
                .load(destination.destinationImage)
                .fit()
                .placeholder(R.drawable.caribbean)
                .into(destinationRowItemBinding.ivDestination)
    }

}