package com.kuvandikov.english.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment(
    @LayoutRes layoutId: Int
): Fragment(layoutId){

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setupListeners()
        setupSubscribers()
    }

    protected open fun initialize() {

    }

    protected open fun setupListeners() {

    }

    protected open fun setupSubscribers(){

    }

}