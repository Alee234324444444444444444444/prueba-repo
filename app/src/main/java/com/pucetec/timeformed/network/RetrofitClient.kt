package com.pucetec.timeformed.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

object RetrofitClient {

    // ✅ Esta URL está bien para el emulador (10.0.2.2 = localhost del PC)
    private const val BASE_URL = "http://192.168.100.6:8080/"

    // ✅ Moshi configurado con soporte para Kotlin data classes
    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory()) // ⬅️ ESTA LÍNEA ES CLAVE
        .build()

    // ✅ Retrofit con Moshi
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ApiService::class.java)
    }
}
