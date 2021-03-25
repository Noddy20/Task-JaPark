package com.demo.japark.utils.extFunctions

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.demo.japark.uiModules.base.BaseAppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

fun AppCompatActivity.setFullScreen() {
    window.apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setDecorFitsSystemWindows(false)
            insetsController?.let { isCont ->
                isCont.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                isCont.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}

fun AppCompatActivity.isActivityNotFullScreen(): Boolean {
    @Suppress("DEPRECATION")
    return ((window.decorView.visibility and View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0)
}

/**
 *    Toolbar Setup
 */

fun BaseAppCompatActivity.setupToolbar(toolbar: MaterialToolbar, titleStr: String, navUpEnabled: Boolean = true){
    //Setup as ActionBar
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        title = titleStr
        if (navUpEnabled) {
            setDisplayShowHomeEnabled(true) // show or hide the default home button
            setDisplayHomeAsUpEnabled(true)
        }
    }
}

fun Activity.makeSceneTransitionAnimationBundle(view: View, transitionName: String? = null) = ActivityOptionsCompat.makeSceneTransitionAnimation(
        this, view, transitionName?:ViewCompat.getTransitionName(view)?:"").toBundle()