package com.example.dadjokes.framework.dataSource.network.mapper

import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.framework.dataSource.network.model.JokeNetworkEntity
import com.example.dadjokes.util.EntityMapper

class JokeNetworkMapper : EntityMapper<JokeNetworkEntity, Joke> {

    fun entityListToJokeList(entityList: ArrayList<JokeNetworkEntity>): ArrayList<Joke>
    {
        val list = ArrayList<Joke>()
        entityList.forEach { entity->
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun jokeListToEntityList(jokeList: ArrayList<Joke>): ArrayList<JokeNetworkEntity>
    {
        val list = ArrayList<JokeNetworkEntity>()
        jokeList.forEach { joke->
           list.add(mapToEntity(joke))
        }
        return list
    }

    override fun mapFromEntity(entity: JokeNetworkEntity): Joke {
        return Joke(
            id = entity.id,
            joke = entity.joke,
            status = entity.status
        )
    }

    override fun mapToEntity(domainModel: Joke): JokeNetworkEntity {
        return JokeNetworkEntity(
            id = domainModel.id,
            joke = domainModel.joke,
            status = domainModel.status
        )
    }


}