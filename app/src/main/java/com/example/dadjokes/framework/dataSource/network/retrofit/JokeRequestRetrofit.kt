package com.example.dadjokes.framework.dataSource.network.retrofit

import com.example.dadjokes.framework.dataSource.network.model.JokeNetworkEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Url

interface JokeRequestRetrofit {

    @GET
    @Headers("Accept: application/json")
    suspend fun getJoke(@Url url: String): Response<JokeNetworkEntity>

}