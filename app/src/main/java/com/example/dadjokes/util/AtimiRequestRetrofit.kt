package com.example.dadjokes.util

import com.example.dadjokes.framework.dataSource.network.abstraction.JokeRetrofitService
import com.example.dadjokes.framework.dataSource.network.retrofit.JokeRequestRetrofit

object AtimiRequestRetrofit {

    fun jokeRequestRetrofit(): JokeRequestRetrofit = AtimiNetwork.getAtimiServiceOf(JokeRequestRetrofit::class.java)
}