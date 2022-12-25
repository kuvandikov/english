package com.kuvandikov.english.presentation.ui.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import com.kuvandikov.english.data.local.db.entities.WordsEntity

@Keep
@Parcelize
data class Word(
    val id: Long,
    val word: String,
    val description: String? = null,
    var isFavourite: Boolean = false,
): Parcelable {
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
