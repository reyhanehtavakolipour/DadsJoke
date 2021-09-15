package com.example.dadjokes.framework.presentation.viewModel

import android.util.Log
import com.example.dadjokes.business.domain.state.StateEvent
import com.example.dadjokes.framework.presentation.BaseViewModel
import com.example.dadjokes.framework.presentation.state.JokeStateEvent
import com.example.dadjokes.framework.presentation.state.JokeViewState
import com.example.dadjokes.util.AtimiInteractor
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.first

@ExperimentalCoroutinesApi
@FlowPreview
class JokeViewModel : BaseViewModel<JokeViewState>() {


    private val jokeInteractor = AtimiInteractor.jokeInteractor()

    override fun handleNewData(data: JokeViewState) {
        data.let { viewState->
            setViewState(viewState)
        }
    }



    override fun setStateEvent(stateEvent: StateEvent) {
        when(stateEvent)
        {
            is JokeStateEvent.GetRandomJokeEvent -> {
                val cacheResultJob = jokeInteractor.getRandomJoke.getRandomJokeFromCache(stateEvent = stateEvent)
                launchJob(stateEvent, cacheResultJob)
                val networkResultJob = jokeInteractor.getRandomJoke.getRandomJokeFromApi(stateEvent = stateEvent)
                CoroutineScope(Dispatchers.IO).launch {
                    networkResultJob.first()?.let { result->
                        if (result.data?.jokeModel?.joke != null)
                        {
                            result.data?.jokeModel?.joke?.isFavourite = false
                            val cacheResultJob2 = jokeInteractor.insertOrUpdateJoke.insertOrUpdateJoke(result.data?.jokeModel?.joke!!, stateEvent)
                            withContext(Dispatchers.Main){
                                launchJob(stateEvent, cacheResultJob2)
                            }
                        }
                        else
                        {
                            withContext(Dispatchers.Main){
                                launchJob(stateEvent, networkResultJob)
                            }
                        }
                    }
                }
            }
            is JokeStateEvent.AddToFavouriteEvent -> {
                val joke = stateEvent.joke
                joke.isFavourite = true
                val cachedResultJob = jokeInteractor.insertOrUpdateJoke.insertOrUpdateJoke(joke = joke, stateEvent = stateEvent)
                launchJob(stateEvent, cachedResultJob)
            }
            is JokeStateEvent.DeleteJokeFromFavouritesEvent -> {
                val joke = stateEvent.joke
                joke.isFavourite = false
                val cachedResultJob = jokeInteractor.insertOrUpdateJoke.insertOrUpdateJoke(joke = joke, isDeleteFromFavouriteList = stateEvent.isDeleteFromFavouriteList, stateEvent = stateEvent)
                launchJob(stateEvent, cachedResultJob)
            }
            is JokeStateEvent.GetAllFavouriteJokesEvent ->{
                val cachedResultJob = jokeInteractor.getFavouriteJokes.getFavouriteJokes(stateEvent = stateEvent)
                launchJob(stateEvent, cachedResultJob)
            }
            is JokeStateEvent.SearchJokesEvent -> {
                val cachedResultJob = jokeInteractor.searchJokes.searchJokes(search = stateEvent.search, stateEvent = stateEvent)
                launchJob(stateEvent, cachedResultJob)
            }
            else ->{
                val invalidJob=  emitInvalidStateEvent(stateEvent)
                launchJob(stateEvent, invalidJob)
            }
        }
    }

    override fun initNewViewState(): JokeViewState {
        return JokeViewState()
    }
}