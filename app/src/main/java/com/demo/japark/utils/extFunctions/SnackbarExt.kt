package com.demo.japark.utils.extFunctions

import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.demo.japark.R
import com.demo.japark.databinding.ContentCustomSnackbarLayoutBinding
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.models.util.ModelSnackbarData
import com.demo.japark.utils.extFunctions.coroutineViewBinding.launchViewClick
import com.google.android.material.snackbar.Snackbar

fun AppCompatActivity.showSnackBar(mModel: ModelSnackbarData, mActionListener: (() -> Unit)? = null): Pair<Snackbar, ContentCustomSnackbarLayoutBinding>? {
    var pair: Pair<Snackbar, ContentCustomSnackbarLayoutBinding>? = null
    mModel.runCatching {
        // Create the Snackbar
        val snackbar = Snackbar.make(coordinatorLayout, "", length)
        // Get the Snackbar's layout view
        val layout = snackbar.view as Snackbar.SnackbarLayout
        layout.findViewById<TextView?>(com.google.android.material.R.id.snackbar_text)?.isVisible = false

        layoutInflater.inflateBinding<ContentCustomSnackbarLayoutBinding>(R.layout.content_custom_snackbar_layout, null).apply {
            bModel = mModel
            if (!action.isNullOrBlank() && mActionListener != null) {
                snackBtn.launchViewClick(lifecycleScope) {
                    mActionListener.invoke()
                }
            }

            layout.apply {
                removeAllViews()
                setPadding(0, 0, 0, 0)
                addView(root)
            }

            executePendingBindings()
            pair = Pair(snackbar, this)
        }
        snackbar.show()
    }
    return pair
}

fun AppCompatActivity.showNetworkStateSnackBar(mNetState: SealedNetState, mRootView: CoordinatorLayout) {
    when (mNetState) {
        is SealedNetState.Available -> { showSnackBar(ModelSnackbarData(getString(R.string.msg_you_are_online), mRootView, R.color.colorGreen)) }
        is SealedNetState.NotAvailable -> { showSnackBar(ModelSnackbarData(getString(R.string.msg_you_are_offline), mRootView, R.color.colorBgSnackBar)) }
    }
}