package com.kuvandikov.english.presentation.ui.dialogs

import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.DialogDetailsBinding
import com.kuvandikov.english.presentation.base.BaseDialog
import com.kuvandikov.english.presentation.ui.model.Word


class DetailsDialog :  BaseDialog<DialogDetailsBinding>(R.layout.dialog_details){

    val args: DetailsDialogArgs by navArgs()

    private var word: Word? = null

    override val binding by viewBinding(DialogDetailsBinding::bind)


    override fun initialize() {

        word = args.word

        word?.apply {
            binding.tvWord.text = word
            binding.tvDescription.text = description
        }

    }



}