package com.demo.japark.uiModules.cityDetails

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.Menu
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.demo.japark.R
import com.demo.japark.databinding.ActivityCityBinding
import com.demo.japark.models.data.ModelJapanCity
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.uiModules.base.BaseAppCompatActivity
import com.demo.japark.utils.GlideApp
import com.demo.japark.utils.extFunctions.getColorAttrCompat
import com.demo.japark.utils.extFunctions.getColorCompat
import com.demo.japark.utils.extFunctions.showNetworkStateSnackBar
import dagger.hilt.android.AndroidEntryPoint
import com.demo.japark.utils.extFunctions.invoke
import com.demo.japark.utils.extFunctions.setupToolbar
import com.demo.japark.utils.extFunctions.toast
import com.bumptech.glide.request.target.Target

@AndroidEntryPoint
class ActivityCity : BaseAppCompatActivity() {

    companion object{

        private const val EXTRA_KEY_MODEL_CITY = "modelCity"

        fun getIntent(mContext: Context, model: ModelJapanCity) =
                Intent(mContext, ActivityCity::class.java).apply {
                    putExtra(EXTRA_KEY_MODEL_CITY, model)
                }
    }

    private val mDataBinding by binding<ActivityCityBinding>(R.layout.activity_city)
    private lateinit var mModelCity: ModelJapanCity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding{

            supportPostponeEnterTransition()

            intent.getParcelableExtra<ModelJapanCity?>(EXTRA_KEY_MODEL_CITY).let { modelCity->
                if (modelCity == null){
                    toast(R.string.err_msg_general)
                    finish()
                }else{
                    mModelCity = modelCity
                    bModel = mModelCity

                    includeToolbar.run {
                        setupToolbar(toolbar, "")

                        collapsingToolbar.title = getString(R.string.title_city)
                        collapsingToolbar.setExpandedTitleColor(getColorCompat(R.color.colorTransparent))
                        collapsingToolbar.setCollapsedTitleTextColor(getColorAttrCompat(R.attr.colorTextMain))
                    }

                    loadCityImage()

                }
            }

        }
    }

    override fun networkStatChanged(netState: SealedNetState) {
        showNetworkStateSnackBar(netState, mDataBinding.actRootView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_theme, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun ActivityCityBinding.loadCityImage(){

        //For SharedElement Transition

        GlideApp.with(this@ActivityCity)
                .asDrawable()
                .load(mModelCity.image)
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
                .into(includeToolbar.ivCity)
    }

}