package com.kuvandikov.english.presentation.extensions

import android.app.Activity
import android.content.res.Resources.NotFoundException
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView.ViewHolder


// region Fragment

/**
 * Fragment Extensions
 */

fun Fragment.showToastShort(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToastShort(@StringRes textFromRes: Int) {
    Toast.makeText(context, textFromRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.showToastLong(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}

fun Fragment.showToastLong(@StringRes textFromRes: Int) {
    Toast.makeText(context, textFromRes, Toast.LENGTH_LONG).show()
}

// endregion


// region View

/**
* View Extensions
*/

fun View.hideKeyboard() {
    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}


fun ViewHolder.getString(@StringRes id: Int): String = try {
    itemView.context.getString(id)
} catch (e: NotFoundException) {
    Log.e("TAG", "ViewHolder.getString: ${e.message}")
    ""
}
