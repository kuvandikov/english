package com.kuvandikov.english.presentation.ui.fragments.main.settings

import android.content.Intent
import android.net.Uri
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.kuvandikov.english.BuildConfig
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.FragmentSettingsBinding
import com.kuvandikov.english.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    private val viewModel: SettingsViewModel by viewModels()


    override fun initialize() {
        val appVersion = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
        binding.appVersion.text = getString(R.string.app_version, appVersion)
    }

    override fun setupSubscribers() {
        lifecycleScope.launchWhenStarted {
            viewModel.unitId.collectLatest {
                if (!it.isNullOrEmpty()) initAds(it)
            }
        }
    }

    override fun setupListeners() {

        binding.rateTheApp.setOnClickListener {
            try {
                val playStoreIntent = Intent(Intent.ACTION_VIEW)
                playStoreIntent.data = Uri.parse("https://play.google.com/store/apps/details?id=uz.loving.math_battle")
                startActivity(playStoreIntent)
                viewModel.firebaseRateTheAppEvent()
            } catch (e: Exception) {
                viewModel.firebaseRateTheAppEvent(e.message)
            }
        }

        binding.feedback.setOnClickListener {
            // https://t.me/ChatRussianUzbekDictionaryBot
            try {
                val telegramIntent = Intent(Intent.ACTION_VIEW)
                telegramIntent.data = Uri.parse("https://t.me/ChatEnglishRussianDictionaryBot")
                startActivity(telegramIntent)
            } catch (e: Exception) {

            }
        }

        binding.shareToFriends.setOnClickListener {
            try {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id=com.kuvandikov.english")
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
                viewModel.firebaseShareToFriendsEvent()
            } catch (e: Exception) {
                viewModel.firebaseShareToFriendsEvent(e.message)
            }

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