package com.example.test.ui.service

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ServiceViewModelFactory(
    private val application: Application,
    private val navigator: Navigator
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ServiceViewModel::class.java)) {
            return ServiceViewModel(application, navigator) as T
        }
        throw IllegalArgumentException("No view model")
    }
}