package com.example.dadjokes.util

import com.example.dadjokes.util.Constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


object AtimiNetwork {
    const val TAG = "CobaltNetwork"
    private val converter = GsonConverterFactory.create()
    private val okHttpClient = getOkHttpClient()
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(MoshiConverterFactory.create())


    fun <T> getAtimiServiceOf(clazz: Class<T>): T {
        return retrofit
                .client(okHttpClient)
                .build()
                .create(clazz)
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // connect timeout
                .writeTimeout(30, TimeUnit. SECONDS) // write timeout
                .readTimeout(30, TimeUnit.SECONDS) // read timeout
                .build()
    }

}