package com.kuvandikov.english.presentation.ui.fragments.details

import android.util.Log
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.FragmentDetailsBinding
import com.kuvandikov.english.presentation.base.BaseFragment
import com.kuvandikov.english.presentation.ui.model.Word
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment: BaseFragment(R.layout.fragment_details){

    private val args: DetailsFragmentArgs by navArgs()

    private var word: Word? = null

    private val binding by viewBinding(FragmentDetailsBinding::bind)


    override fun initialize() {
        word = args.word

        word?.apply {
            binding.tvWord.text = word
            binding.tvDescription.text = description
        }

    }



}