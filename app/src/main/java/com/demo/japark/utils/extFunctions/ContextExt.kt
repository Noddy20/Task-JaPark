package com.demo.japark.utils.extFunctions

import android.content.Context
import android.content.res.TypedArray
import android.net.ConnectivityManager
import android.os.Build
import android.util.TypedValue
import androidx.annotation.AttrRes
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.demo.japark.models.sealed.SealedNetState
import timber.log.Timber


/**
 *   Network Connectivity Functions
 */

fun Context.getNetState(connMan: ConnectivityManager? = null): SealedNetState {
    kotlin.runCatching {
        (connMan ?: applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)?.let { conMan ->
            val capabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                conMan.getNetworkCapabilities(conMan.activeNetwork)
            } else {
                @Suppress("DEPRECATION")
                conMan.activeNetworkInfo
            }
            if (capabilities != null) {
                return SealedNetState.Available
            }
        }
    }.onFailure {
        Timber.e(it, "Exc isNetConnected : ")
    }
    return SealedNetState.NotAvailable.UnAvailable
}

fun Context.isNetConnected(connMan: ConnectivityManager? = null): Boolean {
    return (getNetState(connMan) == SealedNetState.Available)
}


/**
 *  Resource Functions
 */

fun Context.getColorCompat(@ColorRes resId: Int) = ContextCompat.getColor(applicationContext, resId)

fun Context.getColorAttrCompat(@AttrRes resId: Int): Int {
    val typedValue = TypedValue()
    val typedArray: TypedArray = applicationContext.obtainStyledAttributes(typedValue.data, intArrayOf(resId))
    val color = typedArray.getColor(0, 0)
    typedArray.recycle()
    return color
}

