package com.example.test.ui.button

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ButtonViewModel(private val interfaceButton: InterfaceButton) : ViewModel(){

    fun buttonClicked(){
        viewModelScope.launch {
            delay(10000)
            interfaceButton.expiredTime()
        }
    }

}