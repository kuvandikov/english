package com.kuvandikov.english.data.local.db

import android.content.Context
import androidx.room.Room
import com.kuvandikov.english.data.local.db.daos.WordsDao

class RoomClient {

    fun provideAppDatabase(context: Context) = Room
        .databaseBuilder(context, AppDatabase::class.java, "english.db")
        .createFromAsset("database/english.db")
        .build()

    fun provideFooDao(appDatabase: AppDatabase): WordsDao = appDatabase.wordsDao()
}