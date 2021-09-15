package com.example.dadjokes.util

import com.example.dadjokes.business.interactor.JokeIntractors
import com.example.dadjokes.business.interactor.favouriteJokes.GetFavouriteJokes
import com.example.dadjokes.business.interactor.favouriteJokes.SearchJokes
import com.example.dadjokes.business.interactor.randomJoke.GetRandomJoke
import com.example.dadjokes.business.interactor.common.InsertOrUpdateJoke

object AtimiInteractor {

    fun jokeInteractor() = JokeIntractors(
        insertOrUpdateJoke = InsertOrUpdateJoke(),
        getFavouriteJokes = GetFavouriteJokes(),
        getRandomJoke = GetRandomJoke(),
        searchJokes = SearchJokes()
    )
}