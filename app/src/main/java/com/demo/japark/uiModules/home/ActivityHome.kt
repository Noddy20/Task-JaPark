package com.demo.japark.uiModules.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.demo.japark.R
import com.demo.japark.databinding.ActivityHomeBinding
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.uiModules.base.BaseAppCompatActivity
import com.demo.japark.utils.extFunctions.invoke
import com.demo.japark.utils.extFunctions.setupToolbar
import com.demo.japark.utils.extFunctions.showNetworkStateSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityHome : BaseAppCompatActivity() {

    private val mDataBinding by binding<ActivityHomeBinding>(R.layout.activity_home)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mDataBinding{
            setupToolbar(includeToolbar.toolbar, getString(R.string.title_home), false)

            shimmerView.startShimmer()
        }
    }

    override fun networkStatChanged(netState: SealedNetState) {
        showNetworkStateSnackBar(netState, mDataBinding.actRootView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_theme, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuItemChangeTheme -> openThemeChooser()
        }
        return super.onOptionsItemSelected(item)
    }

}