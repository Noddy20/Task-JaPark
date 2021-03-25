package com.demo.japark.uiModules.imagePopup

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.demo.japark.R
import com.demo.japark.databinding.ActivityImagePopupBinding
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.uiModules.base.BaseAppCompatActivity
import com.demo.japark.utils.GlideApp
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

        supportPostponeEnterTransition()

        val title = intent.getStringExtra(EXTRA_KEY_TITLE)
        val url = intent.getStringExtra(EXTRA_KEY_IMAGE_URL)

        if (title.isNullOrBlank() || url.isNullOrBlank()){
            toast(R.string.err_msg_general)
            finish()
        }else{
            mDataBinding{

               /* getColorAttrCompat(R.attr.colorPrimary).let { bgColor ->
                    tvFoodName.setBackgroundColor(bgColor)
                    ivItem.setBackgroundColor(bgColor)
                }*/
                //tvFoodName.setTextColor(getColorAttrCompat(R.attr.colorTextMain))


                bTitle = title
                loadImage(url)

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

    private fun ActivityImagePopupBinding.loadImage(url: String){

        //For SharedElement Transition

        GlideApp.with(this@ActivityImagePopup)
            .asDrawable()
            .load(url)
            .placeholder(R.drawable.placeholder)
            .thumbnail(0.5f)
            .centerCrop()
            .dontAnimate()
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    supportStartPostponedEnterTransition()
                    return false
                }
            })
            .into(ivItem)
    }

}