package com.example.dadjokes.framework.dataSource.cache.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dadjokes.framework.dataSource.cache.model.JokeCacheEntity

@Dao
interface JokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: JokeCacheEntity): Long


    @Query("select * from jokes")
    suspend fun getAllJokes(): List<JokeCacheEntity>?


    @Query("select * from jokes where isFavourite = :isFavourite")
    suspend fun getAllFavouriteJokes(isFavourite: Boolean): List<JokeCacheEntity>

    @Query("update jokes set isFavourite = :isFavourite where id = :id")
    suspend fun deleteJokeFromFavourites(id: String, isFavourite: Boolean): Int


    @Query("SELECT * FROM jokes WHERE isFavourite= :isFavourite AND joke LIKE '%' || :search || '%'")
    suspend fun searchJokes(search: String, isFavourite: Boolean): List<JokeCacheEntity>?

}