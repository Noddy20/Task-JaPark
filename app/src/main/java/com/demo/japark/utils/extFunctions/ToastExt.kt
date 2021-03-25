package com.demo.japark.utils.extFunctions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 *   Extension functions to show toast
 */

fun Context.toast(mMsg: String) {
    Toast.makeText(this, mMsg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes mResId: Int) {
    toast(getString(mResId))
}
