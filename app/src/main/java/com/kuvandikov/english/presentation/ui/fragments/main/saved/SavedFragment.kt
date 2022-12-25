package com.kuvandikov.english.presentation.ui.fragments.main.saved

import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.FragmentSavedBinding
import com.kuvandikov.english.presentation.base.BaseFragment
import com.kuvandikov.english.presentation.extensions.activityNavController
import com.kuvandikov.english.presentation.extensions.navigateSafely
import com.kuvandikov.english.presentation.ui.adapters.WordsAdapter
import com.kuvandikov.english.presentation.ui.adapters.WordsAdapter.Companion.SAVED_VIEW_TYPE
import com.kuvandikov.english.presentation.ui.dialogs.DetailsDialogDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SavedFragment: BaseFragment(R.layout.fragment_saved){


    private val viewModel: SavedViewModel by viewModels()

    private val binding by viewBinding(FragmentSavedBinding::bind)

    private val adapter = WordsAdapter(SAVED_VIEW_TYPE)


    override fun initialize() {

        binding.list.layoutManager = LinearLayoutManager(this.context)
        binding.list.adapter = adapter

        lifecycleScope.launchWhenStarted {
            Log.d("TAG", "initialize: SavedFragment")
            viewModel.initData()
        }
    }

    override fun setupSubscribers() {
        lifecycleScope.launchWhenStarted {
            viewModel.list.collectLatest {
                adapter.updateItems(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.noFoundTextVisibility.collectLatest {
                binding.savedNotFoundText.isVisible = it
            }
        }
    }

    override fun setupListeners() {
        adapter.clickRemoveSaved = { word->
            viewModel.removeSaved(word)
        }

        adapter.click = {word ->
            //val action = DetailsFragmentDirections.actionGlobalDetailsFragment(word)
            val action = DetailsDialogDirections.actionGlobalDetailsDialog(word)
            activityNavController().navigateSafely(action)
        }

    }
}