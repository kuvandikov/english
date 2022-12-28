package com.kuvandikov.english.presentation.ui.dialogs

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.DialogDetailsBinding
import com.kuvandikov.english.presentation.base.BaseDialog
import com.kuvandikov.english.presentation.ui.model.Word
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsDialog :  BaseDialog<DialogDetailsBinding>(R.layout.dialog_details){

    val args: DetailsDialogArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels()

    private var word: Word? = null

    override val binding by viewBinding(DialogDetailsBinding::bind)


    override fun initialize() {

        word = args.word

        word?.apply {
            binding.tvWord.text = word
            binding.tvDescription.text = description
            binding.imagePlayButton.isVisible = canBeAudio
        }

    }


    override fun setupListeners() = with(binding){
        imagePlayButton.setOnClickListener {
            if (word == null) return@setOnClickListener
            val path = "${context!!.cacheDir}/${word!!.word}.mp3"

            Log.d("TAG", "setupListeners: $path")
            viewModel.playAudio(word!!.id,path)
        }

        closeButton.setOnClickListener {
            dismiss()
        }
    }



}