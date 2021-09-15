package com.example.dadjokes.business.interactor.favouriteJokes

import android.util.Log
import com.example.dadjokes.business.data.cache.abstraction.JokeCacheDataSource
import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.business.domain.state.*
import com.example.dadjokes.framework.presentation.state.JokeViewState
import com.example.dadjokes.util.AtimiCacheDataSourceImpl
import com.example.dadjokes.util.CacheResponseHandler
import com.example.dadjokes.util.Constants
import com.example.dadjokes.util.safeCacheCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class
SearchJokes {

    private lateinit var jokeCacheDataSource: JokeCacheDataSource


    fun searchJokes(search: String, stateEvent: StateEvent): Flow<DataState<JokeViewState>?> = flow {

        val cacheResult = safeCacheCall(Dispatchers.IO){
            setJokeCacheDataSource()
            jokeCacheDataSource.searchJokes(search)
        }

        val response = object : CacheResponseHandler<JokeViewState, List<Joke>>(
            response = cacheResult,
            stateEvent = stateEvent){

            override suspend fun handleSuccess(resultObj: List<Joke>): DataState<JokeViewState> {
                return DataState.data(
                    data = JokeViewState(favouriteJokes = resultObj as ArrayList<Joke>),
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