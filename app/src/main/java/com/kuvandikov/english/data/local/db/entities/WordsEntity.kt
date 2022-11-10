package com.kuvandikov.english.data.local.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "Words")
class WordsEntity (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Long?,

    val word: String?,

    val description: String?,

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val audio: ByteArray?,

    val frequency: Long?,

    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean
)