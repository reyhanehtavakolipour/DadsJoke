package com.example.dadjokes.framework.presentation.state

import com.example.dadjokes.business.domain.mode.Joke

class JokeViewState(
    var jokeModel: JokeModel?= null,
    var favouriteJokes: ArrayList<Joke>?= null,
){
    override fun toString(): String {
        return "joke= $jokeModel," +
                "favouriteJokes= $favouriteJokes"
    }
}

data class JokeModel(
    var joke: Joke,
    var isDeleteFromFavouriteList: Boolean ?= null
)