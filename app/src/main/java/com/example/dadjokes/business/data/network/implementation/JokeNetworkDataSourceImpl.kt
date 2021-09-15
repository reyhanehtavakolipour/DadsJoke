package com.example.dadjokes.business.data.network.implementation

import android.util.Log
import com.example.dadjokes.business.data.network.abstraction.JokeNetworkDataSource
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.util.AtimiRequestRetrofit
import com.example.dadjokes.util.AtimiRetrofitServiceImpl

class JokeNetworkDataSourceImpl: JokeNetworkDataSource {

    private val jokeRetrofitDataSource = AtimiRetrofitServiceImpl.jokeRetrofitServiceImpl()

    override suspend fun getJoke(): Joke {
        return jokeRetrofitDataSource.getJoke()
    }
}
