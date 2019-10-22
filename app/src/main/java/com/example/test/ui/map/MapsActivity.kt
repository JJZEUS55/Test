package com.example.test.ui.map

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.test.R
import com.example.test.databinding.ActivityMapsBinding

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import java.util.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        btn_random.setOnClickListener {
            val randomPoints = edt_random.text.toString().toIntOrNull()
            mMap.clear()
            if(randomPoints != null){
                if(::mMap.isInitialized){
                    for (i in 0 .. randomPoints){
                        val randomLatLon = randomLatLon()
                        mMap.addMarker(MarkerOptions().position(randomLatLon))
                    }
                } else {
                    Toast.makeText(applicationContext, getString(R.string.wait_map), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, getString(R.string.toast_just_number), Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    private fun randomLatLon(): LatLng {
        val randomNumber = Random().nextDouble()
        val lat = (randomNumber * -180.0) + 90.0
        val lon = (randomNumber * -360.0) + 180.0
        return LatLng(lat, lon)
    }
}
