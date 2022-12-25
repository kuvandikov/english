package com.kuvandikov.english.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.kuvandikov.english.presentation.extensions.hideKeyboard

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


    fun hideKeyboard(){
        requireView().hideKeyboard()
    }
}