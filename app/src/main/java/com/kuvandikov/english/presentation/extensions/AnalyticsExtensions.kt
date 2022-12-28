package com.kuvandikov.english.presentation.extensions

import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import com.kuvandikov.english.presentation.ui.model.Word

fun FirebaseAnalytics.logSearch(term: String) = logEvent(FirebaseAnalytics.Event.SEARCH){
    param(FirebaseAnalytics.Param.SEARCH_TERM,term)
}

fun FirebaseAnalytics.logOpenWordDialog(word: Word) = logEvent("open_word_dialog"){
    param("id",word.id)
    param("word",word.word)
    param("description", "${word.description}")
    param("isFavourite","${word.isFavourite}")
    param("canBeAudio","${word.canBeAudio}")
}

fun FirebaseAnalytics.logSetFavorite(word: Word) = logEvent("set_favorite_word"){
    param("id",word.id)
    param("word",word.word)
    param("description", "${word.description}")
    param("isFavourite","${word.isFavourite}")
    param("canBeAudio","${word.canBeAudio}")
}

fun FirebaseAnalytics.logPlayAudioWord(word: Word) = logEvent("play_audio_word"){
    param("id",word.id)
    param("word",word.word)
    param("description", "${word.description}")
    param("isFavourite","${word.isFavourite}")
    param("canBeAudio","${word.canBeAudio}")
}

fun FirebaseAnalytics.logPlayAudioWordIOException(word: Word) = logEvent("play_audio_word_exception"){
    param("id",word.id)
    param("word",word.word)
    param("description", "${word.description}")
    param("isFavourite","${word.isFavourite}")
    param("canBeAudio","${word.canBeAudio}")
}


fun FirebaseAnalytics.logPlayAudioWordNull() = logEvent("play_audio_word_null",null)

fun FirebaseAnalytics.logRateTheApp(exception: String? = null) = logEvent("rate_the_app"){
    param("exception","$exception")
}

fun FirebaseAnalytics.logShareToFriends(exception: String? = null) = logEvent("share_to_friends"){
    param("exception","$exception")
}

