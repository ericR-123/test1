package com.location.codechallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import com.location.codechallenge.repository.models.Site
import com.location.codechallenge.util.Constants.USA_LATLONG
import com.location.codechallenge.viewmodel.MapsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val mapsViewModel: MapsViewModel by viewModels()
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        (supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment)?.getMapAsync(this)
    }

    private fun displayAllLocations(allLocations: List<Site>) {
        allLocations.forEach {
            googleMap.addMarker(MarkerOptions().position(it.location))
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(USA_LATLONG))

        mapsViewModel.getAllLocations().observe(
            this@MapsActivity,
            Observer {
                displayAllLocations(it)
            }
        )
    }
}
