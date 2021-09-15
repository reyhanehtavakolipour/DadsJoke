package com.example.dadjokes.util

import com.example.dadjokes.framework.dataSource.cache.database.AppDatabase

object AtimiDao {

    val appDb = AppDatabase.getDatabase(BaseApplication.applicationContext())

    fun jokeDao() = appDb.jokeDao()
}