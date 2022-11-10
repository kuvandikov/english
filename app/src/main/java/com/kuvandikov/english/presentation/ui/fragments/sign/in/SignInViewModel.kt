package com.kuvandikov.english.presentation.ui.fragments.sign.`in`

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
class SignInViewModel  @Inject constructor(
    private val dao: WordsDao
): ViewModel() {


    private val _list = MutableStateFlow<MutableList<Word>>(mutableListOf())
    val list: StateFlow<MutableList<Word>> = _list


    init {
        viewModelScope.launch(Dispatchers.IO) {
            _list.value = dao.get().map {
                Word(it.id?:0,it.word?:"",it.description)
            }.toMutableList()
        }

    }
}