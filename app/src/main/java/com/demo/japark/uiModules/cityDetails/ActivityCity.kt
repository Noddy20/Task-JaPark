package com.demo.japark.uiModules.cityDetails

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.demo.japark.R
import com.demo.japark.databinding.ActivityCityBinding
import com.demo.japark.models.data.ModelJapanCity
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.uiModules.base.BaseAppCompatActivity
import com.demo.japark.utils.extFunctions.getColorAttrCompat
import com.demo.japark.utils.extFunctions.getColorCompat
import com.demo.japark.utils.extFunctions.showNetworkStateSnackBar
import dagger.hilt.android.AndroidEntryPoint
import com.demo.japark.utils.extFunctions.invoke
import com.demo.japark.utils.extFunctions.setupToolbar
import com.demo.japark.utils.extFunctions.toast

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

            //Here I am getting model from list as Parcelable because it contains small amount of data
            //If the data is in large amount I can also load this item by it's name from persisted data
            // by -> CitiesDatabase.citiesDao().loadSingle

            intent.getParcelableExtra<ModelJapanCity?>(EXTRA_KEY_MODEL_CITY).let { modelCity->
                if (modelCity == null){
                    toast(R.string.err_msg_general)
                    finish()
                }else{
                    mModelCity = modelCity
                    bModel = mModelCity

                    includeToolbar.run {
                        // Extension function to setup the toolbar
                        setupToolbar(toolbar, getString(R.string.title_city))

                        // This code is to hide the toolbar title when collapsing toolbar is expanded
                        collapsingToolbar.title = getString(R.string.title_city)
                        collapsingToolbar.setExpandedTitleColor(getColorCompat(R.color.colorTransparent))
                    }

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

}