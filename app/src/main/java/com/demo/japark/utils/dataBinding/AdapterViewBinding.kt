package com.demo.japark.utils.dataBinding

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.facebook.shimmer.ShimmerFrameLayout

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

    @JvmStatic
    @BindingAdapter(value = ["bind_startShimmer"], requireAll = false)
    fun startStopShimmerView(view: ShimmerFrameLayout, startShimmer: Boolean) {
        if (startShimmer) {
            view.startShimmer()
            view.isVisible = true
        } else {
            view.stopShimmer()
            view.isVisible = false
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["bind_playLottie", "bind_lottieFile"], requireAll = false)
    fun startLottieAnimView(view: LottieAnimationView, startShimmer: Boolean, fileName: String?) {
        if (!fileName.isNullOrBlank()){
            view.setAnimation(fileName)
        }
        if (startShimmer) {
            view.playAnimation()
            view.isVisible = true
        } else {
            view.pauseAnimation()
            view.isVisible = false
        }
    }


}