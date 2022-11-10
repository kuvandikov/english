package com.kuvandikov.english.presentation.ui.fragments.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kuvandikov.english.data.local.db.daos.WordsDao
import com.kuvandikov.english.presentation.ui.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: WordsDao,
) : ViewModel() {

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

    private val _filter_result = MutableStateFlow<MutableList<Word>>(mutableListOf())
    val filter_result: StateFlow<MutableList<Word>> = _filter_result


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = dao.get().map {
                Word(it.id ?: 0, it.word ?: "", it.description,it.isFavourite)
            }
            _list.value = result.toMutableList()
            _filter_result.value = result.toMutableList()
        }

    }

    fun filterWithQuery(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (query.isNotEmpty()) {
                _filter_result.value = dao.search(query).map {
                    Word(it.id ?: 0, it.word ?: "", it.description,it.isFavourite)
                }.toMutableList()
                _layoutSimilarVisibility.value = true
                _layoutWordsVisibility.value = false
                _clearOrVoiceQueryVisibility.value = true

                _noSearchResultsFoundTextVisibility.value = _filter_result.value.isEmpty()
            } else if (query.isEmpty()) {
                _filter_result.value = dao.get().map {
                    Word(it.id ?: 0, it.word ?: "", it.description,it.isFavourite)
                }.toMutableList()
                _layoutSimilarVisibility.value = false
                _layoutWordsVisibility.value = true
                _clearOrVoiceQueryVisibility.value = false
                _noSearchResultsFoundTextVisibility.value = false
            }
        }
    }

    fun setFavorite(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            dao.save(word.toEntity())
        }
    }
}