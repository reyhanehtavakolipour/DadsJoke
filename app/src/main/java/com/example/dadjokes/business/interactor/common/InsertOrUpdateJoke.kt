package com.example.dadjokes.business.interactor.common

import android.util.Log
import com.example.dadjokes.business.data.cache.abstraction.JokeCacheDataSource
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.business.domain.state.*
import com.example.dadjokes.framework.presentation.state.JokeModel
import com.example.dadjokes.framework.presentation.state.JokeViewState
import com.example.dadjokes.util.AtimiCacheDataSourceImpl
import com.example.dadjokes.util.CacheResponseHandler
import com.example.dadjokes.util.Constants.ADDED_TO_FAVOURITE
import com.example.dadjokes.util.safeCacheCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class InsertOrUpdateJoke {

    private lateinit var jokeCacheDataSource: JokeCacheDataSource


    fun insertOrUpdateJoke(joke: Joke, stateEvent: StateEvent, isDeleteFromFavouriteList: Boolean?= null): Flow<DataState<JokeViewState>?> = flow {
        val cachedResult = safeCacheCall(Dispatchers.IO){
            setJokeCacheDataSource()
            jokeCacheDataSource.insertJoke(
                Joke(id = joke.id,
                    joke = joke.joke,
                    status = joke.status,
                    isFavourite = joke.isFavourite))

        }

        val response = object : CacheResponseHandler<JokeViewState, Long>(
            response = cachedResult,
            stateEvent = stateEvent){

            override suspend fun handleSuccess(resultObj: Long): DataState<JokeViewState> {
                return DataState.data(
                    data = JokeViewState(jokeModel = JokeModel(joke = joke, isDeleteFromFavouriteList = isDeleteFromFavouriteList)),
                    stateEvent = stateEvent
                )
            }

        }.getResult()
        emit(response)
    }

    private fun setJokeCacheDataSource()
    {
        jokeCacheDataSource= AtimiCacheDataSourceImpl.jokeCacheDataSourceImpl()
    }
}