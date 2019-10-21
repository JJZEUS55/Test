package com.example.test.network

import android.app.Service
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL = "http://upaxdev.com/ws/webresources/generic/getData/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ServiceTest {
    @Headers("Content-Type: application/json")
    @POST(".")
    suspend fun getZip(@Body serviceUser: ServiceUser): ResponseService
}

object ServiceApi{
    val retrofitService: ServiceTest by lazy {
        retrofit.create(ServiceTest::class.java)
    }
}