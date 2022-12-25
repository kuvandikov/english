package com.kuvandikov.english.data.local.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kuvandikov.english.data.local.db.entities.WordsEntity


@Dao
interface WordsDao {
    @Query("SELECT * from Words")
    fun get(): MutableList<WordsEntity>

    @Query("SELECT * from Words where is_favourite == 1")
    fun getSaved(): MutableList<WordsEntity>

    @Query("SELECT * from Words WHERE word LIKE :search_query || '%'")
    fun search(search_query: String): MutableList<WordsEntity>

    @Query("SELECT COUNT(*) FROM Words")
    fun getCount(): Long

    @Query("DELETE FROM Words")
    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(post: WordsEntity)

}