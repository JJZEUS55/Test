package com.example.test.ui.service

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.test.R

class ServiceActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this, ServiceViewModelFactory(application)).get(ServiceViewModel::class.java)
    }
    private var onCompleteDownload: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        onCompleteDownload = object :BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                viewModel.extractZip()
            }
        }
        viewModel.getUrlZip()
        registerReceiver(onCompleteDownload, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onDestroy() {
        if(onCompleteDownload != null){
            unregisterReceiver(onCompleteDownload)
        }
        super.onDestroy()
    }
}
