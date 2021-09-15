package com.example.dadjokes.business.domain.mode

import java.util.*
import kotlin.collections.ArrayList

class JokeFactory {

    fun createSingleJoke(
        id: String,
        joke: String,
        status: String
    ): Joke {
        return Joke(
            id = id ,
            joke = joke,
            status = status
        )
    }

    fun createJokeList(numJokes: Int): List<Joke> {
        val list: ArrayList<Joke> = ArrayList()
        for(i in 0 until numJokes){ // exclusive on upper bound
            list.add(
                createSingleJoke(
                    id = UUID.randomUUID().toString(),
                    joke = UUID.randomUUID().toString(),
                    status = UUID.randomUUID().toString()
                )
            )
        }
        return list
    }
}