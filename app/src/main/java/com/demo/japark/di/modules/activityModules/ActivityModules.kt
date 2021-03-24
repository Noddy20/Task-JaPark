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
object AppModule {

    @Provides
    @ActivityScoped
    fun provideDefaultExceptionHandler(mActivity: Activity) = AppDialogs(mActivity)

}