package com.demo.japark.di.modules.activityModules

import android.app.Activity
import com.demo.japark.uiModules.dialogs.AppDialogs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModules {

    @Provides
    @ActivityScoped
    fun provideAppDialogs(mActivity: Activity) = AppDialogs(mActivity)

}