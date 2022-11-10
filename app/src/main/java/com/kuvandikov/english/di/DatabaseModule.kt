package com.kuvandikov.english.di

import android.content.Context
import com.kuvandikov.english.data.local.db.AppDatabase
import com.kuvandikov.english.data.local.db.RoomClient
import com.kuvandikov.english.data.local.db.daos.WordsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    val roomClient = RoomClient()

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = roomClient.provideAppDatabase(context)

    @Singleton
    @Provides
    fun provideWordsDao(
        appDatabase: AppDatabase
    ): WordsDao = roomClient.provideFooDao(appDatabase)
}