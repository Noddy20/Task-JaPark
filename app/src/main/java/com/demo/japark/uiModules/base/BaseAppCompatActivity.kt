package com.demo.japark.uiModules.base

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.demo.japark.R
import com.demo.japark.data.AppSharedPrefs
import com.demo.japark.models.sealed.SealedNetState
import com.demo.japark.uiModules.dialogs.AppDialogs
import com.demo.japark.utils.InternetUtil
import com.demo.japark.utils.observeNetState
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

abstract class BaseAppCompatActivity : AppCompatActivity() {

    @Inject
    @ApplicationContext
    lateinit var mAppContext: Context

    @Inject
    lateinit var mInternetUtil: InternetUtil

    @Inject
    lateinit var mAppPrefs: AppSharedPrefs

    @Inject
    lateinit var mDialogs: AppDialogs

    protected var registerNetReceiver: Boolean = true

    protected inline fun <reified T : ViewDataBinding> binding(@LayoutRes resId: Int): Lazy<T>
            = lazy { DataBindingUtil.setContentView<T>(this, resId).apply {
        lifecycleOwner = this@BaseAppCompatActivity
    }}

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            window.statusBarColor = Color.BLACK
            window.navigationBarColor = Color.BLACK
        }
    }

    override fun onResume() {
        super.onResume()
        if (registerNetReceiver) {
            mInternetUtil.observeNetState(this, ::networkStatChanged)
        }
    }

    override fun onPause() {
        super.onPause()
        if (registerNetReceiver)
            unregisterNetReceiver()
    }

    override fun onDestroy() {
        mDialogs.dismiss()
        if (registerNetReceiver)
            unregisterNetReceiver()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
            R.id.menuItemChangeTheme -> openThemeChooser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun unregisterNetReceiver() {
        kotlin.runCatching {
            mInternetUtil.removeObservers(this)
        }.onFailure { Timber.e(it, "unregisterNetReceiver : ") }
    }

    abstract fun networkStatChanged(netState: SealedNetState)


    private fun openThemeChooser(){
        mDialogs.showCountDownSelectDialog(mAppPrefs.selectedThemeMode){
            mAppPrefs.selectedThemeMode = it
            AppCompatDelegate.setDefaultNightMode(it)
            delegate.applyDayNight()
        }
    }


}
