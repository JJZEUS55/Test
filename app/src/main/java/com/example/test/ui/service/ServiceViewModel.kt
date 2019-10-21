package com.example.test.ui.service

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.network.ServiceApi
import com.example.test.network.ServiceUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ServiceViewModel : ViewModel() {


    fun getUrlZip() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val serviceUser = ServiceUser(55, 12984)
                val string = ServiceApi.retrofitService.getZip(serviceUser)
                Log.d("URL", string.toString())
            }
        }
    }

}