package com.example.travelplannerapp.adapters.destination_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.travelplannerapp.R
import com.example.travelplannerapp.listeners.OnItemClickListener
import com.example.travelplannerapp.model.Destination
import java.util.*

class DestinationAdapter(private var onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<DestinationViewHolder>(), Filterable {

    private var destinationList = mutableListOf<Destination>()
    private var filteredDestinationList = mutableListOf<Destination>()

    fun setListData(data: MutableList<Destination>) {
        destinationList = data
        filteredDestinationList = data
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DestinationViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.destination_row_item, parent, false)
        return DestinationViewHolder(view)
    }

    override fun onBindViewHolder(holder: DestinationViewHolder, position: Int) {
        val currentDestination = destinationList[position]
        holder.bind(currentDestination)
        holder.itemView.setOnClickListener { onItemClickListener.onItemClick(currentDestination) }
    }

    override fun getItemCount(): Int {
        return destinationList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    filterResults.count = filteredDestinationList.size
                    filterResults.values = filteredDestinationList
                } else {
                    val searchCharacter = "$constraint".toLowerCase(Locale.getDefault())
                    val destinations = mutableListOf<Destination>()
                    for (destination in filteredDestinationList) {
                        if (destination.destinationName!!.toLowerCase(Locale.getDefault()).contains(searchCharacter)) {
                            destinations.add(destination)
                        }
                    }
                    filterResults.count = destinations.size
                    filterResults.values = destinations
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                destinationList = results!!.values as MutableList<Destination>
                notifyDataSetChanged()
            }

        }
    }
}