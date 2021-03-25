package com.demo.japark.uiModules.imagePopup

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.demo.japark.R
import com.demo.japark.databinding.ActivityImagePopupBinding
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.uiModules.base.BaseAppCompatActivity
import com.demo.japark.utils.extFunctions.coroutineViewBinding.launchViewClick
import com.demo.japark.utils.extFunctions.invoke
import com.demo.japark.utils.extFunctions.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityImagePopup : BaseAppCompatActivity() {

    companion object {
        private const val EXTRA_KEY_TITLE = "title"
        private const val EXTRA_KEY_IMAGE_URL = "image_url"

        fun getIntent(mContext: Context, title: String?, imgUrl: String?) =
            Intent(mContext, ActivityImagePopup::class.java).apply {
                putExtra(EXTRA_KEY_TITLE, title)
                putExtra(EXTRA_KEY_IMAGE_URL, imgUrl)
            }
    }

    private val mDataBinding by binding<ActivityImagePopupBinding>(R.layout.activity_image_popup)

    override fun onCreate(savedInstanceState: Bundle?) {
        window {
            // To receive MotionEvent.ACTION_OUTSIDE
            setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            )
            setFlags(
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
            )
        }

        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra(EXTRA_KEY_TITLE)
        val url = intent.getStringExtra(EXTRA_KEY_IMAGE_URL)

        if (title.isNullOrBlank() || url.isNullOrBlank()){
            toast(R.string.err_msg_general)
            finish()
        }else{
            mDataBinding{
                bTitle = title
                bUrl = url

                ivClose.launchViewClick(lifecycleScope){
                    onBackPressed()
                }

            }
        }
    }

    override fun networkStatChanged(netState: SealedNetState) {
        toast(if (netState == SealedNetState.Available) R.string.msg_you_are_online else R.string.msg_you_are_offline)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        // If we've received a touch notification that the user has touched
        // outside the dialog-UI, call onBackPressed.
        if (MotionEvent.ACTION_OUTSIDE == event.action) {
            onBackPressed()
            return true
        }
        return super.onTouchEvent(event)
    }

}