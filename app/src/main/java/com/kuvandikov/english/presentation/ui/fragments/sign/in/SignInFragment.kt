package com.kuvandikov.english.presentation.ui.fragments.sign.`in`

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.FragmentSignInBinding
import com.kuvandikov.english.presentation.base.BaseFragment
import com.kuvandikov.english.presentation.ui.adapters.WordsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SignInFragment: BaseFragment(R.layout.fragment_sign_in) {

    private val viewModel: SignInViewModel by viewModels()
    private val binding by viewBinding(FragmentSignInBinding::bind)


    override fun initialize() {

    }

}