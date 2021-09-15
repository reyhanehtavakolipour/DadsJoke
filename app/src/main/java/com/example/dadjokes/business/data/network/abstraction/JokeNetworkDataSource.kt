package com.example.dadjokes.business.data.network.abstraction

import com.example.dadjokes.business.domain.mode.Joke

interface JokeNetworkDataSource {

    suspend fun getJoke(): Joke
}