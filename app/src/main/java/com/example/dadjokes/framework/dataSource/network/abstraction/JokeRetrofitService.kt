package com.example.dadjokes.framework.dataSource.network.abstraction

import com.example.dadjokes.business.domain.mode.Joke

interface JokeRetrofitService {


    suspend fun getJoke(): Joke
}