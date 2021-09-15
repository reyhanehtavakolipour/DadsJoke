package com.example.dadjokes.framework.dataSource.network.implementation

import android.util.Log
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.framework.dataSource.network.abstraction.JokeRetrofitService
import com.example.dadjokes.framework.dataSource.network.model.JokeNetworkEntity
import com.example.dadjokes.framework.dataSource.network.url.jokeApi
import com.example.dadjokes.util.AtimiNetworkMapper
import com.example.dadjokes.util.AtimiRequestRetrofit
import com.example.dadjokes.util.GenericApiResponse

class JokeRetrofitServiceImpl: JokeRetrofitService {

    private val jokeRequestRetrofit = AtimiRequestRetrofit.jokeRequestRetrofit()
    private val jokeNetworkMapper = AtimiNetworkMapper.jokeNetworkMapper()


    override suspend fun getJoke(): Joke {
        val apiResponse = callRandomJokeApi()
        if (apiResponse.isSuccessFull)
            return jokeNetworkMapper.mapFromEntity(apiResponse.apiResponse?.body()!!)
        else return Joke(id = "-1", joke = "-1", status = "-1")
    }

    private suspend fun callRandomJokeApi(): GenericApiResponse<JokeNetworkEntity>
    {
        val result = jokeRequestRetrofit.getJoke(url = jokeApi())
        return GenericApiResponse(isSuccessFull = result.isSuccessful, apiResponse = result)
    }

}