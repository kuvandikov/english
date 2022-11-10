package com.kuvandikov.english.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kuvandikov.english.data.local.db.daos.WordsDao
import com.kuvandikov.english.data.local.db.entities.WordsEntity

@Database(entities = [WordsEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDao
}