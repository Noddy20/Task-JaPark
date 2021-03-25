package com.demo.japark.uiModules.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.demo.japark.R
import com.demo.japark.databinding.ActivityHomeBinding
import com.demo.japark.models.BaseResponse
import com.demo.japark.models.ResponseStatus
import com.demo.japark.models.data.ModelMainResponse
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.uiModules.base.BaseAppCompatActivity
import com.demo.japark.uiModules.gotoCityDetailsActivity
import com.demo.japark.uiModules.gotoImagePopupActivity
import com.demo.japark.utils.extFunctions.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ActivityHome : BaseAppCompatActivity() {

    private val mDataBinding by binding<ActivityHomeBinding>(R.layout.activity_home)

    private val mViewModel by viewModels<ViewModelHome>()

    @Inject
    lateinit var mAdapterCities: AdapterHomeCities
    @Inject
    lateinit var mAdapterFoods: AdapterHomeFoods

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding{
            // Extension function to setup the toolbar
            setupToolbar(includeToolbar.toolbar, getString(R.string.title_home), false)

            rvCities.adapter = mAdapterCities
            mAdapterCities.onClickItem = {modelCity, iv ->
                gotoCityDetailsActivity(modelCity,
                    makeSceneTransitionAnimationBundle(iv, getString(R.string.transition_name_item_detail_img)))
            }

            rvFood.adapter = mAdapterFoods
            mAdapterFoods.onClickItem = {modelFood, iv->
                gotoImagePopupActivity(modelFood.name, modelFood.image,
                    makeSceneTransitionAnimationBundle(iv, getString(R.string.transition_name_item_detail_img)))
            }

            swipeRefreshLayout.setOnRefreshListener {
                mViewModel.clearPersistedData {
                    mViewModel.callMainDataApi()
                }
            }

            setDataObserver()
            mViewModel.callMainDataApi()

        }
    }

    override fun networkStatChanged(netState: SealedNetState) {
        showNetworkStateSnackBar(netState, mDataBinding.actRootView)

        // Auto call API if no data is available in list and netState is Available
        if (mDataBinding.lottieViewError.isVisible && netState == SealedNetState.Available){
            mViewModel.callMainDataApi()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Clear Cache option is added to clear the data from persisted storage
        // To test the API error handling
        if (item.itemId == R.id.menuItemClearCache){
            mViewModel.clearPersistedData {
                toast(R.string.msg_cache_cleared)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setDataObserver(){
        mViewModel.mainDataLD.observe(this){
            Timber.d("Response_Main $it")
            mDataBinding.setDataUi(it)
        }
    }

    private fun ActivityHomeBinding.setDataUi(baseResponse: BaseResponse<ModelMainResponse?>){
        if (swipeRefreshLayout.isRefreshing) swipeRefreshLayout.isRefreshing = false

        when(baseResponse.status){
            ResponseStatus.SUCCESS -> {
                mAdapterCities.submitList(baseResponse.data?.cities)
                mAdapterFoods.submitList(baseResponse.data?.foods)
            }
            else -> {
                tvErrorMsg.text = baseResponse.message?:getString(R.string.err_msg_general)
            }
        }

        Timber.d("Response_Main setDataUi ${mAdapterCities.itemCount} ${mAdapterFoods.itemCount} ${baseResponse.status}")
        //Only update the status if both the lists are empty or Status is Success/Empty_Data
        //So that if API call fails, persisted data will remain on UI
        if ((mAdapterCities.itemCount <= 0 && mAdapterFoods.itemCount <= 0) ||
            (baseResponse.status != ResponseStatus.SUCCESS || baseResponse.status != ResponseStatus.NO_DATA)) {
            bStatus = baseResponse.status
        }
    }

}