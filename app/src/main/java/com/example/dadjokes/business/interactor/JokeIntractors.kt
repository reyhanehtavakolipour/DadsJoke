package com.example.dadjokes.business.interactor

import com.example.dadjokes.business.interactor.favouriteJokes.GetFavouriteJokes
import com.example.dadjokes.business.interactor.favouriteJokes.SearchJokes
import com.example.dadjokes.business.interactor.randomJoke.GetRandomJoke
import com.example.dadjokes.business.interactor.common.InsertOrUpdateJoke

class JokeIntractors(
    val getFavouriteJokes: GetFavouriteJokes,
    val insertOrUpdateJoke: InsertOrUpdateJoke,
    val getRandomJoke: GetRandomJoke,
    val searchJokes: SearchJokes
)