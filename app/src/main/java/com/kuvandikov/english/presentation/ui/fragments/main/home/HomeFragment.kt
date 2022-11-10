package com.kuvandikov.english.presentation.ui.fragments.main.home

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.util.Log
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.FragmentHomeBinding
import com.kuvandikov.english.presentation.base.BaseFragment
import com.kuvandikov.english.presentation.ui.adapters.WordsAdapter
import com.yandex.mobile.ads.banner.AdSize
import com.yandex.mobile.ads.banner.BannerAdEventListener
import com.yandex.mobile.ads.common.AdRequest
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.*


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {


    private val viewModel: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)

    val TAG = "TAG"


    override fun initialize() {

        binding.list.layoutManager = LinearLayoutManager(this.context)
        binding.listSimilar.layoutManager = LinearLayoutManager(this.context)

        initAds()
    }

    override fun setupSubscribers() {
        lifecycleScope.launchWhenStarted {
            viewModel.clearOrVoiceQueryVisibility.collectLatest {
                binding.searchBoxContainer.clearSearchQuery.isVisible = it
                binding.searchBoxContainer.voiceSearchQuery.isVisible = !it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.layoutWordsVisibility.collectLatest {
                binding.layoutWords.isVisible = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.layoutSimilarVisibility.collectLatest {
                binding.layoutSimilar.isVisible = it
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.list.collectLatest {
                val adapter = WordsAdapter(it)
                binding.list.adapter = adapter
                adapter.clickFavorite = { word->
                    word.isFavourite = !word.isFavourite
                    adapter.notifyDataSetChanged()
                    viewModel.setFavorite(word)
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.filter_result.collectLatest {
                val adapter = WordsAdapter(it)
                binding.listSimilar.adapter = adapter
                adapter.clickFavorite = { word->
                    word.isFavourite = !word.isFavourite
                    adapter.notifyDataSetChanged()
                    viewModel.setFavorite(word)
                }
            }
        }


        lifecycleScope.launchWhenStarted {
            viewModel.noSearchResultsFoundTextVisibility.collectLatest {
                binding.noSearchResultsFoundText.isVisible = it
            }
        }

    }

    override fun setupListeners() {
        binding.searchBoxContainer.searchEditText.doOnTextChanged { text, _, _, _ ->
            val query = text.toString().toLowerCase(Locale.getDefault())
            viewModel.filterWithQuery(query)
        }

        binding.searchBoxContainer.clearSearchQuery.setOnClickListener {
            binding.searchBoxContainer.searchEditText.setText("")
        }
        binding.searchBoxContainer.voiceSearchQuery.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE,
                    "ru-RU"
                )
            }
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        }
    }

    private fun initAds() {
        binding.bannerAdView.setAdUnitId("R-M-1947472-1")


        binding.bannerAdView.setAdSize(AdSize.BANNER_320x50)

        val adRequest = AdRequest.Builder().build()

        // Регистрация слушателя для отслеживания событий, происходящих в баннерной рекламе.
        binding.bannerAdView.setBannerAdEventListener(object : BannerAdEventListener {
            override fun onAdLoaded() {
                Log.d(TAG, "onAdLoaded: ")
            }

            override fun onAdFailedToLoad(error: AdRequestError) {
                Log.d(TAG, "onAdFailedToLoad: ${error.code}: ${error.description}")
            }

            override fun onAdClicked() {
                Log.d(TAG, "onAdClicked: ")
            }

            override fun onLeftApplication() {
                Log.d(TAG, "onLeftApplication: ")
            }

            override fun onReturnedToApplication() {
                Log.d(TAG, "onReturnedToApplication: ")
            }

            override fun onImpression(p0: ImpressionData?) {
                Log.d(TAG, "onImpression: ")
            }

        })

        binding.bannerAdView.loadAd(adRequest)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                }
            // Do something with spokenText
            binding.searchBoxContainer.searchEditText.setText(spokenText)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


    companion object {
        const val SPEECH_REQUEST_CODE = 0
    }


}