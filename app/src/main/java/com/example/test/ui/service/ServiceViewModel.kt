package com.example.test.ui.service

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.network.ServiceApi
import com.example.test.network.ServiceUser
import ir.mahdi.mzip.zip.ZipArchive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.Files.isDirectory
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.test.network.LocationFile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.StringBuilder
import android.icu.lang.UCharacter.GraphemeClusterBreak.T




class ServiceViewModel(private val application: Application) : ViewModel() {

    fun getUrlZip() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val serviceUser = ServiceUser(55, 12984)
                val responseService = ServiceApi.retrofitService.getZip(serviceUser)
                Log.d("URL", responseService.toString())
                downloadFile(responseService.valueResponse)
            }
        }
    }

    private fun downloadFile(url: String) {
        val requestDownload = DownloadManager.Request(Uri.parse(url))
        requestDownload.setDescription("Archivo Zip")
        requestDownload.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        Log.d("downloadFile", url)

        val managerDownload =
            application.getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager

        managerDownload?.enqueue(
            requestDownload.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("Archivo.zip")
                .setDescription("Archivo Zip")
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Archivo.zip")
        )
    }

    fun extractZip() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ZipArchive.unzip("/sdcard/Download/Archivo.zip", "/sdcard/Download/", "")
                readJson()
            }
        }
    }

    private fun readJson() {
        var gson = Gson()
        val bufferReader: BufferedReader = File("/sdcard/Download/cargaInicial_2019-10-21_12984.txt").bufferedReader()
        val inputString = bufferReader.use {
            it.readText()
        }

        val string = StringBuilder()
        inputString.forEach {
            string.append(it)
        }

        Log.d("json", string.toString())

        val listLocations = object : TypeToken<List<LocationFile>>() {}.type
        val post = gson.fromJson<List<LocationFile>>(inputString, listLocations)

        Log.d("objectPost", "$post")

    }

}