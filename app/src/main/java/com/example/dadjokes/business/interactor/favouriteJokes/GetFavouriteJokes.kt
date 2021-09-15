package com.example.dadjokes.business.interactor.favouriteJokes

import com.example.dadjokes.business.data.cache.abstraction.JokeCacheDataSource
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.business.domain.state.*
import com.example.dadjokes.framework.presentation.state.JokeViewState
import com.example.dadjokes.util.*
import com.example.dadjokes.util.Constants.GET_FAVOURITE_JOKES_SUCCESS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFavouriteJokes {

    private lateinit var jokeCacheDataSource: JokeCacheDataSource


    fun getFavouriteJokes(stateEvent: StateEvent): Flow<DataState<JokeViewState>?> = flow {

        val cacheResult = safeCacheCall(Dispatchers.IO){
            setJokeCacheDataSource()
            jokeCacheDataSource.getAllFavouriteJokes()
        }

        val response = object : CacheResponseHandler<JokeViewState, ArrayList<Joke>>(
            response = cacheResult,
            stateEvent = stateEvent){

            override suspend fun handleSuccess(resultObj: ArrayList<Joke>): DataState<JokeViewState> {
                return DataState.data(
                    data = JokeViewState(favouriteJokes = resultObj),
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