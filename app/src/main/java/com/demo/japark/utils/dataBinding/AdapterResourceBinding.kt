package com.demo.japark.utils.dataBinding

import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter

object AdapterResourceBinding {

    @JvmStatic
    @BindingAdapter(value = ["bind_backgroundColorRes"], requireAll = false)
    fun setViewBackgroundColor(view: View, @ColorRes color: Int?) {
        if (color != null) {
            view.setBackgroundResource(color)
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bind_textColorRes"], requireAll = false)
    fun setTextColorResource(view: TextView, @ColorRes color: Int?) {
        if (color != null) view.setTextColor(ContextCompat.getColor(view.context, color))
    }

}