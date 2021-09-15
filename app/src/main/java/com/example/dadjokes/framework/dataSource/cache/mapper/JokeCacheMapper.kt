package com.example.dadjokes.framework.dataSource.cache.mapper

import com.example.dadjokes.business.domain.mode.Joke
import com.example.dadjokes.framework.dataSource.cache.model.JokeCacheEntity
import com.example.dadjokes.util.EntityMapper

class JokeCacheMapper : EntityMapper<JokeCacheEntity, Joke> {


    fun entityListToJokeList(entityList: ArrayList<JokeCacheEntity>): ArrayList<Joke>
    {
        val list = ArrayList<Joke>()
        entityList.forEach { entity->
            list.add(mapFromEntity(entity))
        }
        return list
    }


    fun jokeListToEntityList(jokeList: ArrayList<Joke>): ArrayList<JokeCacheEntity>
    {
        val list = ArrayList<JokeCacheEntity>()
        jokeList.forEach { joke->
            list.add(mapToEntity(joke))
        }
        return list
    }


    override fun mapFromEntity(entity: JokeCacheEntity): Joke {
        return Joke(
            id = entity.id,
            joke = entity.joke,
            status = entity.status,
            isFavourite = entity.isFavourite
        )
    }

    override fun mapToEntity(domainModel: Joke): JokeCacheEntity {
        return JokeCacheEntity(
            id = domainModel.id,
            joke = domainModel.joke,
            status = domainModel.status,
            isFavourite = domainModel.isFavourite
        )
    }
}