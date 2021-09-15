package com.example.dadjokes.business.data.cache.implementation

import com.example.dadjokes.business.data.cache.abstraction.JokeCacheDataSource
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.util.AtimiDaoServiceImpl

class JokeCacheDataSourceImpl : JokeCacheDataSource{

    private val jokeDaoServiceImpl = AtimiDaoServiceImpl.jokeDaoServiceImpl()

    override suspend fun insertJoke(joke: Joke): Long {
        return jokeDaoServiceImpl.insertJoke(joke)
    }

    override suspend fun getAllJokes(): ArrayList<Joke> {
        return jokeDaoServiceImpl.getAllJokes()
    }

    override suspend fun getAllFavouriteJokes(): ArrayList<Joke> {
        return jokeDaoServiceImpl.getAllFavouriteJokes()
    }

    override suspend fun deleteJokeFromFavourites(joke: Joke): Int {
        return jokeDaoServiceImpl.deleteJokeFromFavourites(joke)
    }

    override suspend fun getRandomJoke(): Joke? {
        return jokeDaoServiceImpl.getRandomJoke()
    }

    override suspend fun searchJokes(search: String): ArrayList<Joke>? {
        return jokeDaoServiceImpl.searchJokes(search)
    }
}