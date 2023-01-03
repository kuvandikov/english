package com.kuvandikov.english.presentation.ui.fragments.main.settings

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.kuvandikov.english.data.local.preferences.PreferencesHelper
import com.kuvandikov.english.presentation.extensions.logRateTheApp
import com.kuvandikov.english.presentation.extensions.logShareToFriends
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val mFirebaseAnalytics: FirebaseAnalytics,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {


    private val _unitId = MutableStateFlow<String?>(null)
    val unitId: StateFlow<String?> = _unitId

    init {
        _unitId.value = preferencesHelper.getSettingsUnitId()
    }


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