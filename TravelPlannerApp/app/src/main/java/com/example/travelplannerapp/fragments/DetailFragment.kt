package com.example.travelplannerapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.travelplannerapp.activities.NewPlanActivity
import com.example.travelplannerapp.databinding.FragmentDetailBinding
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private lateinit var fragmentDetailBinding: FragmentDetailBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        fragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        //  Data from DestinationFragment
        val bundle: Bundle? = requireActivity().intent.extras
        val destinationName = bundle!!.getString("destinationName")
        val destinationCountry = bundle.getString("destinationCountry")
        val destinationDate = bundle.getString("destinationDate")
        val destinationDescription = bundle.getString("destinationDescription")
        val destinationImage = bundle.getString("destinationImage")
        val destinationImage1 = bundle.getString("destinationImage1")
        val destinationImage2 = bundle.getString("destinationImage2")
        val destinationImage3 = bundle.getString("destinationImage3")
        val destinationLocation = bundle.getString("destinationLocation")
        val destinationPrice = bundle.getInt("destinationPrice")

        fragmentDetailBinding.tvDetailDestinationName.text = destinationName
        fragmentDetailBinding.tvDetailDestinationCountry.text = destinationCountry
        fragmentDetailBinding.tvDetailDestinationDate.text = destinationDate
        fragmentDetailBinding.tvDetailDestinationDescription.text = destinationDescription
        Picasso.get().load(destinationImage).into(fragmentDetailBinding.ivDetailDestination)
        Picasso.get().load(destinationImage1).into(fragmentDetailBinding.ivImage1)
        Picasso.get().load(destinationImage2).into(fragmentDetailBinding.ivImage2)
        Picasso.get().load(destinationImage3).into(fragmentDetailBinding.ivImage3)
        fragmentDetailBinding.tvDetailDestinationLocation.text = destinationLocation
        "€$destinationPrice (per person, daily)".also { fragmentDetailBinding.tvDetailDestinationPrice.text = it }

        fragmentDetailBinding.ivArrowBack.setOnClickListener { requireActivity().onBackPressed() }
        fragmentDetailBinding.bPlan.setOnClickListener {
            Intent(context, NewPlanActivity::class.java).apply {
                putExtra("planDestinationName", destinationName)
                putExtra("planDestinationImage", destinationImage)
                putExtra("planDestinationPrice", destinationPrice)
                startActivity(this)
            }
        }


        return fragmentDetailBinding.root
    }


}