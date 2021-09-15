package com.example.dadjokes.framework.presentation.state

import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.business.domain.state.StateEvent

sealed class JokeStateEvent : StateEvent{

    class GetRandomJokeEvent(): JokeStateEvent() {

        override fun errorInfo(): String {
            return "error getting random joke!"
        }

        override fun eventName(): String {
            return "GetRandomJokeEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean = true
    }


    class AddToFavouriteEvent(var joke: Joke): JokeStateEvent() {
        override fun errorInfo(): String {
            return "error inserting joke to local db"
        }

        override fun eventName(): String {
            return "InsertJokeToLocalDb"
        }

        override fun shouldDisplayProgressBar(): Boolean = true
    }


    class DeleteJokeFromFavouritesEvent(var joke: Joke, var isDeleteFromFavouriteList: Boolean?= null): JokeStateEvent() {
        override fun errorInfo(): String {
            return "error deleting joke from favourites"
        }

        override fun eventName(): String {
            return "DeleteJokeFromFavouritesEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean = true
    }


    class GetAllFavouriteJokesEvent() : JokeStateEvent() {
        override fun errorInfo(): String {
            return "error getting all favourite jokes"
        }

        override fun eventName(): String {
            return "GetAllFavouriteJokesEvent"
        }

        override fun shouldDisplayProgressBar(): Boolean = true
    }


    class SearchJokesEvent(var search: String): JokeStateEvent() {
        override fun errorInfo(): String {
            return "error searching joke"
        }

        override fun eventName(): String {
            return "SearchJokes"
        }

        override fun shouldDisplayProgressBar(): Boolean = true
    }
}