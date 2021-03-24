package com.demo.japark.utils.extFunctions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

fun <T : ViewDataBinding> LayoutInflater.inflateBinding(
    @LayoutRes resId: Int,
    container: ViewGroup?,
    attachToParent: Boolean = false
): T {
    return DataBindingUtil.inflate(this, resId, container, attachToParent)
}