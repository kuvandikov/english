package com.kuvandikov.english.presentation.ui.dialogs

import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.analytics.FirebaseAnalytics
import com.kuvandikov.english.data.local.db.daos.WordsDao
import com.kuvandikov.english.presentation.extensions.logOpenWordDialog
import com.kuvandikov.english.presentation.extensions.logPlayAudioWord
import com.kuvandikov.english.presentation.extensions.logPlayAudioWordIOException
import com.kuvandikov.english.presentation.extensions.logPlayAudioWordNull
import com.kuvandikov.english.presentation.ui.model.Word
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dao: WordsDao,
    private val mFirebaseAnalytics: FirebaseAnalytics
) : ViewModel() {

    private val mediaPlayer = MediaPlayer()

    fun firebaseWordDialogOpenEvent(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            mFirebaseAnalytics.logOpenWordDialog(word)
        }
    }



    fun playAudio(word: Word, path: String) {

        viewModelScope.launch(Dispatchers.IO) {
            mediaPlayer.reset()
            val wordEntity = dao.getById(word.id)
            val audioByteArray = wordEntity.audio ?: return@launch

            try {
                val audioFile = File(path)

                val fileOutputStream = FileOutputStream(audioFile)
                /*val fileInputStream = FileOutputStream(audioFile)*/
                fileOutputStream.write(audioByteArray)
                fileOutputStream.close()
                mediaPlayer.setDataSource(path)
                mediaPlayer.prepare()
                mediaPlayer.start()
                mFirebaseAnalytics.logPlayAudioWord(word)
                Log.d("TAG", "playAudio: ")

            } catch (e: IOException) {
                firebasePlayAudioIOExceptionEvent(word)
                Log.d("TAG", "playAudio: ${e.message}")
            }
        }
    }

    private fun firebasePlayAudioIOExceptionEvent(word: Word){
        viewModelScope.launch(Dispatchers.IO) {
            mFirebaseAnalytics.logPlayAudioWordIOException(word)
        }
    }

    fun firebasePlayAudioWordNullEvent(){
        viewModelScope.launch(Dispatchers.IO) {
            mFirebaseAnalytics.logPlayAudioWordNull()
        }
    }


}