package com.demo.japark.utils

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.demo.japark.R

@GlideModule
class GlideAppModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val memoryCacheSizeBytes = 1024 * 1024 * 20                                                 // 20mb disk & memory cache size
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
        builder.setDiskCache(InternalCacheDiskCacheFactory(context, memoryCacheSizeBytes.toLong()))
        builder.setDefaultRequestOptions(defaultRequestOptions)

        super.applyOptions(context, builder)
    }

    private val defaultRequestOptions = RequestOptions()
        .signature(
            ObjectKey(
                System.currentTimeMillis() / (24 * 60 * 60 * 1000)
            )
        )
        .fitCenter()
        .placeholder(null)
        .error(null)
        .encodeFormat(Bitmap.CompressFormat.PNG)
        .encodeQuality(100)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .format(DecodeFormat.PREFER_ARGB_8888)
        .skipMemoryCache(false)

    companion object {

        fun getRequestOptions(
            isCenterCrop: Boolean = true,
            @DrawableRes @ColorRes placeHolderResId: Int? = R.color.colorTransparent,
            @DrawableRes @ColorRes errorResId: Int? = R.color.colorTransparent,
            mSkipMemoryCache: Boolean = false
        ): RequestOptions {
            return RequestOptions().apply {
                if (isCenterCrop)
                    centerCrop()
                else
                    fitCenter()
                if (placeHolderResId != null) placeholder(placeHolderResId) else placeholder(null)
                if (errorResId != null) error(errorResId) else error(null)
                skipMemoryCache(mSkipMemoryCache)
            }
        }

    }
}