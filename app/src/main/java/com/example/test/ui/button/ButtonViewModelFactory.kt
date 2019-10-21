package com.example.test.ui.button

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ButtonViewModelFactory (private val interfaceButton: InterfaceButton): ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ButtonViewModel::class.java)){
            return ButtonViewModel(interfaceButton) as T
        }
        throw IllegalArgumentException("No view model")

    }
}