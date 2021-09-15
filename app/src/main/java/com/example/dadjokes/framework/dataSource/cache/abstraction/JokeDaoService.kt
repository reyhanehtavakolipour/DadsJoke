package com.example.dadjokes.framework.dataSource.cache.abstraction

import com.example.dadjokes.business.domain.mode.Joke

interface JokeDaoService {

    suspend fun insertJoke(joke: Joke): Long
    suspend fun getAllJokes(): ArrayList<Joke>
    suspend fun getAllFavouriteJokes(): ArrayList<Joke>
    suspend fun deleteJokeFromFavourites(joke: Joke): Int
    suspend fun getRandomJoke(): Joke?
    suspend fun searchJokes(search: String): List<Joke>?
}