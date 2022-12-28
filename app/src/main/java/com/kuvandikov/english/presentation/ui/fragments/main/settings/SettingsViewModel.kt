package com.kuvandikov.english.presentation.ui.fragments.main.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.kuvandikov.english.data.local.db.daos.WordsDao
import com.kuvandikov.english.presentation.extensions.logPlayAudioWordIOException
import com.kuvandikov.english.presentation.extensions.logRateTheApp
import com.kuvandikov.english.presentation.extensions.logSetFavorite
import com.kuvandikov.english.presentation.extensions.logShareToFriends
import com.kuvandikov.english.presentation.ui.model.Word
import com.yandex.mobile.ads.impl.it
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mFirebaseAnalytics: FirebaseAnalytics
) : ViewModel() {


    fun firebaseRateTheAppEvent(exception: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            mFirebaseAnalytics.logRateTheApp(exception)
        }
    }

    fun firebaseShareToFriendsEvent(exception: String? = null){
        viewModelScope.launch(Dispatchers.IO) {
            mFirebaseAnalytics.logShareToFriends(exception)
        }
    }
}