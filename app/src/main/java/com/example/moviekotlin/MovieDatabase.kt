package com.example.moviekotlin

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    companion object {

        private val DB_NAME = "movie_db"
        private var movieDatabase: MovieDatabase? = null

        fun getInstance(application: Application): MovieDatabase {
            if (movieDatabase == null) {
                movieDatabase = Room.databaseBuilder(
                    application,
                    MovieDatabase::class.java,
                    DB_NAME
                ).build()
            }
            return movieDatabase as MovieDatabase
        }
    }

    abstract val MovieDAO: MovieDAO
}