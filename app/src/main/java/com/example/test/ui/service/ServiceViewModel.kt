package com.example.test.ui.service

import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
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
import androidx.lifecycle.*
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.HttpException


class ServiceViewModel(application: Application, private val navigator: Navigator) : AndroidViewModel(application) {

    private val applicationAndroid = application
    private val _liveReadTxt: MutableLiveData<Boolean> = MutableLiveData()
    val liveRead: LiveData<Boolean>
        get() = _liveReadTxt

    private val _livePositions: MutableLiveData<List<LocationFile>> = MutableLiveData()
    val livePositions: LiveData<List<LocationFile>>
        get() = _livePositions

    private val _liveErrorHttp: MutableLiveData<Int> = MutableLiveData()
    val liveErrorHttp: LiveData<Int>
        get() = _liveErrorHttp

    init {
        _liveReadTxt.value = false
        _liveErrorHttp.value = 0
    }


    fun getUrlZip() {
        viewModelScope.launch {
            navigator.showProgress()
            withContext(Dispatchers.IO) {
                val serviceUser = ServiceUser(55, 12984)
                try {
                    val responseService = ServiceApi.retrofitService.getZip(serviceUser)
                    Log.d("URL", responseService.toString())
                    downloadFile(responseService.valueResponse)
                } catch (e: HttpException) {
                    e.printStackTrace()
                    navigator.hideProgress()
                    _liveErrorHttp.postValue(e.code())
                }

            }
        }
    }

    private fun downloadFile(url: String) {
        val requestDownload = DownloadManager.Request(Uri.parse(url))
        requestDownload.setDescription("Archivo Zip")
        requestDownload.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        Log.d("downloadFile", url)

        val managerDownload =
            applicationAndroid.getSystemService(Context.DOWNLOAD_SERVICE) as? DownloadManager

        managerDownload?.enqueue(
            requestDownload.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle("Archivo.zip")
                .setDescription("Archivo Zip")
                .setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "Archivo.zip"
                )
        )
    }

    fun extractZip() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ZipArchive.unzip("/sdcard/Download/Archivo.zip", "/sdcard/Download/", "")
//                readJson()
                _liveReadTxt.postValue(true)
            }
        }
    }

    fun readJson() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val listLocations: MutableList<LocationFile> = ArrayList()
                var fileTxt: File? = null
                val filesDirectory = File("/sdcard/Download")

                fileTxt = filesDirectory.listFiles()?.find {
                    it.path.contains("cargaInicial") && it.path.endsWith("txt")
                }

                Log.d("fileTxt", "$fileTxt")

                val bufferReader: BufferedReader = File(fileTxt?.path).bufferedReader()
                val inputString = bufferReader.use {
                    it.readText()
                }

                val jsonObject = JSONObject(inputString)
                val jsonArray =
                    (jsonObject.getJSONArray("data").get(0) as? JSONObject)?.getJSONArray("UBICACIONES")

                jsonArray?.let {
                    for (i in 0 until it.length()) {
                        val jsonLocation = it.getJSONObject(i)
                        val locationFile = LocationFile()
                        locationFile.FNLATITUD = jsonLocation.getDouble("FNLATITUD")
                        locationFile.FNLONGITUD = jsonLocation.getDouble("FNLONGITUD")
                        Log.d("locationFile", "$locationFile")
                        listLocations.add(locationFile)
                    }
                    _livePositions.postValue(listLocations)
                }
            }
            navigator.hideProgress()
        }
    }

    fun resetLiveRead() {
        _liveReadTxt.value = false
    }

    fun resetHttpError() {
        _liveErrorHttp.value = 0
    }

}