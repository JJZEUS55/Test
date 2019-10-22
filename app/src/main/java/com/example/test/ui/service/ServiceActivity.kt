package com.example.test.ui.service

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.test.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity(), OnMapReadyCallback, Navigator {

    private var mMap: GoogleMap? = null

    private val viewModel by lazy {
        ViewModelProviders.of(this, ServiceViewModelFactory(application, this))
            .get(ServiceViewModel::class.java)
    }
    private var onCompleteDownload: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        onCompleteDownload = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                viewModel.extractZip()
            }
        }
        viewModel.getUrlZip()
//        viewModel.extractZip()
        registerReceiver(onCompleteDownload, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.liveRead.observe(this, Observer { canReadTxt ->
            canReadTxt?.let {
                if (it) {
                    viewModel.readJson()
                    viewModel.resetLiveRead()
                }
            }
        })

        viewModel.livePositions.observe(this, Observer { listPositions ->
            if (listPositions != null) {
                mMap?.let {
                    listPositions.forEach {locationFile ->
                        val latLon = LatLng(locationFile.FNLATITUD, locationFile.FNLONGITUD)
                        it.addMarker(MarkerOptions().position(latLon))
                    }
                }
            }
        })

        viewModel.liveErrorHttp.observe(this, Observer {error->
            error?.let {
                if(it != 0){
                    Toast.makeText(this, "Error HTTP: $it", Toast.LENGTH_SHORT).show()
                    viewModel.resetHttpError()
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap?.uiSettings?.isZoomControlsEnabled = true
    }

    override fun onDestroy() {
        if (onCompleteDownload != null) {
            unregisterReceiver(onCompleteDownload)
        }
        super.onDestroy()
    }

    override fun showProgress() {
        lyt_progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        lyt_progress.visibility = View.GONE
    }
}
