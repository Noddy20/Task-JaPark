package com.demo.japark.utils.dataBinding

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

object AdapterViewBinding {

    @JvmStatic
    @BindingAdapter(value = ["bind_isVisible"], requireAll = false)
    fun setViewVisibility(view: View, isVisible: Boolean) {
        view.isVisible = isVisible
    }

    @JvmStatic
    @BindingAdapter(value = ["bind_isInvisible"], requireAll = false)
    fun setViewBackgroundColor(view: View, isInvisible: Boolean) {
        view.isInvisible = isInvisible
    }

}