package com.kuvandikov.english.presentation.ui.fragments.main.saved

import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
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

        lifecycleScope.launchWhenStarted {
            viewModel.unitId.collectLatest {
                if (!it.isNullOrEmpty()) initAds(it)
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


    private fun initAds(unitId: String) {


        MobileAds.initialize(context!!) {}
        val adView = AdView(context!!)

        val params = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.gravity = Gravity.BOTTOM

        adView.layoutParams = params

        adView.setAdSize(AdSize.BANNER)

        adView.adUnitId = unitId

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        binding.layout.addView(adView)

    }
}