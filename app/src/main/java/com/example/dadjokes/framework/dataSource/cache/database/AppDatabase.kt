package com.example.dadjokes.framework.dataSource.cache.database

import android.content.Context
import androidx.room.*
import com.example.dadjokes.framework.dataSource.cache.model.JokeCacheEntity


@Database(entities = [JokeCacheEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase  : RoomDatabase(){

    abstract fun jokeDao() : JokeDao

    companion object{
        const val DATABASE_NAME = "joke_db"


        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }
}