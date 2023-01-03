package com.kuvandikov.english.presentation.ui.fragments.main.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.kuvandikov.english.data.local.db.daos.WordsDao
import com.kuvandikov.english.data.local.preferences.PreferencesHelper
import com.kuvandikov.english.presentation.extensions.logSetFavorite
import com.kuvandikov.english.presentation.ui.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedViewModel @Inject constructor(
    private val dao: WordsDao,
    private val mFirebaseAnalytics: FirebaseAnalytics,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    private val _noFoundTextVisibility = MutableStateFlow<Boolean>(false)
    val noFoundTextVisibility: StateFlow<Boolean> = _noFoundTextVisibility

    private val _list = MutableStateFlow<MutableList<Word>>(mutableListOf())
    val list: StateFlow<MutableList<Word>> = _list

    private val _unitId = MutableStateFlow<String?>(null)
    val unitId: StateFlow<String?> = _unitId


    init {
        _unitId.value = preferencesHelper.getSavedUnitId()
    }



    fun initData(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.getSaved().map {
                Word(it.id ?: 0, it.word ?: "", it.description,it.isFavourite,canBeAudio = it.audio != null)
            }
            _list.value = result.toMutableList()

            _noFoundTextVisibility.value = _list.value.isEmpty()
        }
    }

    fun removeSaved(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            mFirebaseAnalytics.logSetFavorite(word.copy(isFavourite = !word.isFavourite))
            _list.value = _list.value.filter { it.id != word.id }.toMutableList()

            val wordEntity = dao.getById(word.id)
            wordEntity.isFavourite = !word.isFavourite
            dao.save(wordEntity)

            _noFoundTextVisibility.value = _list.value.isEmpty()
        }
    }


}