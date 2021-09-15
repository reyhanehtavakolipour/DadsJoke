package com.example.dadjokes.framework.dataSource.cache.implementation

import android.util.Log
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.business.domain.mode.JokeFactory
import com.example.dadjokes.framework.dataSource.cache.abstraction.JokeDaoService
import com.example.dadjokes.framework.dataSource.cache.model.JokeCacheEntity
import com.example.dadjokes.util.AtimiCacheMapper
import com.example.dadjokes.util.AtimiDao

class JokeDaoServiceImpl: JokeDaoService {

    private val jokeDao = AtimiDao.jokeDao()
    private val mapper = AtimiCacheMapper.jokeCacheMapper()

    override suspend fun insertJoke(joke: Joke): Long {
        return jokeDao.insertJoke(mapper.mapToEntity(joke))
    }

    override suspend fun getAllJokes(): ArrayList<Joke> {
        return mapper.entityListToJokeList(jokeDao.getAllJokes() as ArrayList<JokeCacheEntity>)
    }

    override suspend fun getAllFavouriteJokes(): ArrayList<Joke> {
        return mapper.entityListToJokeList(jokeDao.getAllFavouriteJokes(true) as ArrayList<JokeCacheEntity>)
    }

    override suspend fun deleteJokeFromFavourites(joke: Joke): Int {
        return jokeDao.deleteJokeFromFavourites(mapper.mapToEntity(joke).id , false)
    }

    override suspend fun getRandomJoke(): Joke? {
        val jokes = jokeDao.getAllJokes()
        return if (jokes.isNullOrEmpty())
            null
        else {
            val size = jokes.size -1
            mapper.mapFromEntity(jokes[size])
        }
    }

    override suspend fun searchJokes(search: String): ArrayList<Joke> {
       return mapper.entityListToJokeList(jokeDao.searchJokes(search,true) as ArrayList<JokeCacheEntity>)
    }
}