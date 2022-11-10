package com.kuvandikov.english.presentation.ui.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.kuvandikov.english.R
import com.kuvandikov.english.databinding.RussianSearchViewBinding

open class RussianSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : LinearLayout(context, attrs) {
    private val binding = RussianSearchViewBinding.inflate(LayoutInflater.from(context), this, true)

    private var mHint: String = ""
    private var mLabel: String = ""
    private var mText: String = ""
    private var mError: String = ""
    private var mInputType = InputType.TYPE_CLASS_TEXT
    private var mIsInput = true
    private var mLines = 1
    private var mClearable = false
    private var mVoiceEnabled = false
    private var mShowBackButton = false

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RussianSearchView, 0, 0)
        try {
            if (attributes.hasValue(R.styleable.RussianSearchView_inputVoiceEnabled)) {
                mVoiceEnabled =
                    attributes.getBoolean(R.styleable.RussianSearchView_inputVoiceEnabled, true)
            }
            if (attributes.hasValue(R.styleable.RussianSearchView_inputHint)) {
                mHint = attributes.getString(R.styleable.RussianSearchView_inputHint)!!
            }
            if (attributes.hasValue(R.styleable.RussianSearchView_inputLabel)) {
                mLabel = attributes.getString(R.styleable.RussianSearchView_inputLabel)!!
            }
            if (attributes.hasValue(R.styleable.RussianSearchView_inputText)) {
                mText = attributes.getString(R.styleable.RussianSearchView_inputText)!!
            }
            if (attributes.hasValue(R.styleable.RussianSearchView_android_inputType)) {
                mInputType = attributes.getInt(
                    R.styleable.RussianSearchView_android_inputType,
                    InputType.TYPE_CLASS_TEXT
                )
            }
            if (attributes.hasValue(R.styleable.RussianSearchView_android_lines)) {
                mLines = attributes.getInt(R.styleable.RussianSearchView_android_lines, 1)
            }
            if (attributes.hasValue(R.styleable.RussianSearchView_inputClearable)) {
                mClearable =
                    attributes.getBoolean(R.styleable.RussianSearchView_inputClearable, false)
            }
            if (attributes.hasValue(R.styleable.RussianSearchView_inputVoiceEnabled)) {
                mVoiceEnabled =
                    attributes.getBoolean(R.styleable.RussianSearchView_inputVoiceEnabled, false)
            }
            if (attributes.hasValue(R.styleable.RussianSearchView_inputShowBackButton)) {
                mShowBackButton =
                    attributes.getBoolean(R.styleable.RussianSearchView_inputShowBackButton, false)
            }

        } finally {
            attributes.recycle()
        }

        View.inflate(getContext(), R.layout.russian_search_view, this)

        if (mHint.isNotEmpty()) {
            binding.input.hint = mHint
        }

        if (mText.isNotEmpty()) {
            binding.input.setText(mText)
        }


        binding.input.setOnFocusChangeListener { _, hasFocus ->

            if (hasFocus) {
                Log.d("TAG", "focused: $isFocusable $isFocused")
            } else {
                Log.d("TAG", "focuse lose: $isFocusable $isFocused")
            }
        }

    }

    override fun setOnClickListener(listener: OnClickListener?) {
        super.setOnClickListener {listener?.onClick(this)}
    }



}