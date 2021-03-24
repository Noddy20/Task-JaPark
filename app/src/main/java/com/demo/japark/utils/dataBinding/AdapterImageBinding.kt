package com.demo.japark.utils.dataBinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.demo.japark.utils.GlideApp

object AdapterImageBinding {

    @JvmStatic
    @BindingAdapter(value = [
        "bind_glideUrl",
        "bind_glideRes",
        "bind_glideIsCenterCrop",
        "bind_glidePlaceholder",
        "bind_glideError",
        "bind_glideSkipMemoryCache",
        "bind_glideDiskCacheStrategyNone"], requireAll = false)
    fun loadImageWithGlide(
        imgView: ImageView, imageUrl: String?, imageRes: Int?, isCenterCrop: Boolean,
        placeholderRes: Int?, errorRes: Int?, skipMemCache: Boolean, isStrategyNone: Boolean
    ) {
        GlideApp.with(imgView)
            .load(imageUrl?:imageRes)
            .skipMemoryCache(skipMemCache)
            .diskCacheStrategy(if (isStrategyNone) DiskCacheStrategy.NONE else DiskCacheStrategy.AUTOMATIC)
            .apply {
                if (isCenterCrop) centerCrop()
                if (placeholderRes != null) placeholder(placeholderRes)
                if (errorRes != null) error(errorRes)
            }.into(imgView)
    }

}