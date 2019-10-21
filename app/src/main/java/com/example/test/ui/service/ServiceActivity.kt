package com.example.test.ui.service

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.test.R

class ServiceActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ServiceViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)

        viewModel.getUrlZip()
    }
}
