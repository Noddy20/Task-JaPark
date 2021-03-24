package com.demo.japark

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.demo.japark.data.AppSharedPrefs
import com.demo.japark.utils.DefaultExceptionHandler
import com.demo.japark.utils.InternetUtil
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApp: Application() {

    @Inject
    lateinit var myExceptionHandler: DefaultExceptionHandler

    @Inject
    lateinit var mAppPrefs: AppSharedPrefs

    @Inject
    lateinit var mInternetUtil: InternetUtil

    @Inject
    lateinit var mLogUtilTree: Timber.DebugTree   // We can extend Tree class of Timber to make our own custom Error reporting Util for Production version

    override fun onCreate() {
        super.onCreate()

        Timber.plant(mLogUtilTree)

        Thread.setDefaultUncaughtExceptionHandler(myExceptionHandler) // Init Uncaught Exception Handler

        AppCompatDelegate.setDefaultNightMode(mAppPrefs.selectedThemeMode)

        // Init InternetUtil Observer
        mInternetUtil.registerObserver()

    }

}