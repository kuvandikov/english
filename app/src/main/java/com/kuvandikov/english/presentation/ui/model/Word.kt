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
    val canBeAudio: Boolean,
): Parcelable
