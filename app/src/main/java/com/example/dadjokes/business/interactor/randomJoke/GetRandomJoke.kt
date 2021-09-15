package com.example.dadjokes.business.interactor.randomJoke

import android.util.Log
import com.example.dadjokes.business.data.cache.abstraction.JokeCacheDataSource
import com.example.dadjokes.business.data.network.abstraction.JokeNetworkDataSource
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.business.domain.state.*
import com.example.dadjokes.framework.presentation.state.JokeModel
import com.example.dadjokes.framework.presentation.state.JokeViewState
import com.example.dadjokes.util.*
import com.example.dadjokes.util.Constants.GET_JOKE_SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetRandomJoke {
    private lateinit var jokeNetworkDataSource : JokeNetworkDataSource
    private lateinit var jokeCacheDataSource: JokeCacheDataSource


    fun getRandomJokeFromApi(stateEvent: StateEvent): Flow<DataState<JokeViewState>?> = flow {
        val networkResult = safeApiCall(Dispatchers.IO) {
            setJokeNetworkDataSource()
            jokeNetworkDataSource.getJoke()
        }

        val response = object : ApiResponseHandler<JokeViewState, Joke>(
            response = networkResult,
            stateEvent = stateEvent)
        {
            override suspend fun handleSuccess(resultObj: Joke): DataState<JokeViewState> {
                return DataState.data(
                    data = JokeViewState(jokeModel = JokeModel(joke = resultObj)),
                    stateEvent = stateEvent
                )
            }


        }.getResult()
        emit(response)
    }

    fun getRandomJokeFromCache(stateEvent: StateEvent): Flow<DataState<JokeViewState>?> = flow {
        val networkResult = safeCacheCall(Dispatchers.IO) {
            setJokeCacheDataSource()
            jokeCacheDataSource.getRandomJoke()
        }

        val response = object : CacheResponseHandler<JokeViewState, Joke>(
            response = networkResult,
            stateEvent = stateEvent)
        {
            override suspend fun handleSuccess(resultObj: Joke): DataState<JokeViewState> {
                return DataState.data(
                    data = JokeViewState(jokeModel = JokeModel(joke = resultObj)),
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

    private fun setJokeNetworkDataSource()
    {
        jokeNetworkDataSource = AtimiNetworkDataSourceImpl.jokeNetworkDataSourceImpl()
    }
}