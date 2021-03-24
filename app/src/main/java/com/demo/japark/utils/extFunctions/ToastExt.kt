package com.demo.japark.utils.extFunctions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(mMsg: String) {
    Toast.makeText(this, mMsg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(@StringRes mResId: Int) {
    toast(getString(mResId))
}

fun Context.toastLong(mMsg: String) {
    Toast.makeText(this, mMsg, Toast.LENGTH_LONG).show()
}

fun Context.toastLong(@StringRes mResId: Int) {
    toastLong(getString(mResId))
}