package com.example.travelplannerapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.travelplannerapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private var destinationLat: Double = -34.0
    private var destinationLng: Double = 151.0
    lateinit var destinationName: String

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        val destinationCoordinates = LatLng(destinationLat, destinationLng)
        val zoomLevel = 6.8f
        googleMap.addMarker(MarkerOptions().position(destinationCoordinates).title(destinationName))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(destinationCoordinates, zoomLevel))

    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val bundle: Bundle? = requireActivity().intent.extras
        destinationLat = bundle!!.getDouble("destinationLat")
        destinationLng = bundle.getDouble("destinationLng")
        destinationName = bundle.getString("destinationName")!!
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}