package com.example.dadjokes.framework.dataSource.cache.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class JokeCacheEntity(

    @NonNull
    @PrimaryKey(autoGenerate = false)
    var id: String,
    var joke: String,
    var status: String,
    var isFavourite: Boolean? = null)