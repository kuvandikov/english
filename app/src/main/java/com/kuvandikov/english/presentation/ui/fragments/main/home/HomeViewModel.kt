package com.kuvandikov.english.presentation.ui.fragments.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.kuvandikov.english.data.local.db.daos.WordsDao
import com.kuvandikov.english.data.local.preferences.PreferencesHelper
import com.kuvandikov.english.presentation.extensions.logSearch
import com.kuvandikov.english.presentation.extensions.logSetFavorite
import com.kuvandikov.english.presentation.ui.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: WordsDao,
    private val mFirebaseAnalytics: FirebaseAnalytics,
    private val preferencesHelper: PreferencesHelper
) : ViewModel() {

    private val _unitId = MutableStateFlow<String?>(null)
    val unitId: StateFlow<String?> = _unitId

    private val _noSearchResultsFoundTextVisibility = MutableStateFlow<Boolean>(false)
    val noSearchResultsFoundTextVisibility: StateFlow<Boolean> = _noSearchResultsFoundTextVisibility

    private val _clearOrVoiceQueryVisibility = MutableStateFlow<Boolean>(false)
    val clearOrVoiceQueryVisibility: StateFlow<Boolean> = _clearOrVoiceQueryVisibility

    private val _layoutSimilarVisibility = MutableStateFlow<Boolean>(false)
    val layoutSimilarVisibility: StateFlow<Boolean> = _layoutSimilarVisibility

    private val _layoutWordsVisibility = MutableStateFlow<Boolean>(true)
    val layoutWordsVisibility: StateFlow<Boolean> = _layoutWordsVisibility


    private val _list = MutableStateFlow<MutableList<Word>>(mutableListOf())
    val list: StateFlow<MutableList<Word>> = _list

    private val _filterResult = MutableStateFlow<MutableList<Word>>(mutableListOf())
    val filterResult: StateFlow<MutableList<Word>> = _filterResult

    private var _query = "".lowercase(Locale.getDefault())


    init {
        _unitId.value = preferencesHelper.getHomeUnitId()
    }



    fun initData(){
        filterWithQuery(_query)
    }

    fun filterWithQuery(query: String) {
        _query = query
        viewModelScope.launch(Dispatchers.IO) {
            if (_query.isNotEmpty()) {
                mFirebaseAnalytics.logSearch(query)
                _filterResult.value = dao.search(query).map {
                    Word(it.id ?: 0, it.word ?: "", it.description,it.isFavourite, canBeAudio = it.audio != null)
                }.toMutableList()
                _layoutSimilarVisibility.value = true
                _layoutWordsVisibility.value = false
                _clearOrVoiceQueryVisibility.value = true

                _noSearchResultsFoundTextVisibility.value = _filterResult.value.isEmpty()
            } else if (_query.isEmpty()) {
                _list.value = dao.get().map {
                    Word(it.id ?: 0, it.word ?: "", it.description,it.isFavourite,canBeAudio = it.audio != null)
                }.toMutableList()
                _layoutSimilarVisibility.value = false
                _layoutWordsVisibility.value = true
                _clearOrVoiceQueryVisibility.value = false
                _noSearchResultsFoundTextVisibility.value = false
            }
        }
    }

    fun setFavorite(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            mFirebaseAnalytics.logSetFavorite(word.copy(isFavourite = !word.isFavourite))
            _filterResult.value = _filterResult.value.map {
                if (it.id == word.id) it.copy(isFavourite = !word.isFavourite)
                else it
            }.toMutableList()

            _list.value = _list.value.map {
                if (it.id == word.id) it.copy(isFavourite = !word.isFavourite)
                else it
            }.toMutableList()

            val wordEntity = dao.getById(word.id)
            wordEntity.isFavourite = !word.isFavourite

            dao.save(wordEntity)
        }
    }
}