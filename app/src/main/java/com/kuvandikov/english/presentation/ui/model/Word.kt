package com.kuvandikov.english.presentation.ui.model

import com.kuvandikov.english.data.local.db.entities.WordsEntity

data class Word(
    val id: Long,
    val word: String,
    val description: String? = null,
    var isFavourite: Boolean = false,
) {
    fun toEntity(): WordsEntity {
        return WordsEntity(
            id = id,
            word = word,
            description = description,
            audio = null,
            frequency = null,
            isFavourite = isFavourite
        )
    }
}
