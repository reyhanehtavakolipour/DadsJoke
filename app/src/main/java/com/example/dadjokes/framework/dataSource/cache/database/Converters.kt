package com.example.dadjokes.framework.dataSource.cache.database

import androidx.room.TypeConverter
import com.example.dadjokes.framework.dataSource.cache.model.JokeCacheEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {


    @TypeConverter
    fun fromJokeList(jokeList: ArrayList<JokeCacheEntity>?): String? {
        if (jokeList == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<JokeCacheEntity?>() {}.type
        return gson.toJson(jokeList, type)
    }

    @TypeConverter
    fun toJokeList(jokeString: String?): ArrayList<JokeCacheEntity>
    ? {
        if (jokeString == null) {
            return null
        }
        val gson = Gson()
        val type = object :
            TypeToken<ArrayList<JokeCacheEntity>?>() {}.type
        return gson.fromJson<ArrayList<JokeCacheEntity>>(jokeString, type)
    }

}