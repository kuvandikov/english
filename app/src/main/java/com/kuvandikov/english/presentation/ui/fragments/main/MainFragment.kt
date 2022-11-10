package com.kuvandikov.english.presentation.ui.fragments.main

import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.FragmentMainBinding
import com.kuvandikov.english.presentation.base.BaseNavHostFragment

class MainFragment : BaseNavHostFragment(
    R.layout.fragment_main, R.id.nav_host_fragment_main
) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun setupNavigation() {
        binding.bottomNavigation.setupWithNavController(navController)
    }
}