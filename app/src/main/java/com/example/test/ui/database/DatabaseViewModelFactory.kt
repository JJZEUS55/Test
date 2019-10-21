package com.example.test.ui.database

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.lang.IllegalArgumentException

class DatabaseViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DatabaseViewModel::class.java)) {
            return DatabaseViewModel(application) as T
        }
        throw IllegalArgumentException("No view model")
    }
}