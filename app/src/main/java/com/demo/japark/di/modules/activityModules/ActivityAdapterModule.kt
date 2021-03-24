package com.demo.japark.di.modules.activityModules

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.demo.japark.uiModules.home.AdapterHomeCities
import com.demo.japark.uiModules.home.AdapterHomeFoods
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityAdapterModule {

    @Provides
    @ActivityScoped
    fun provideAdapterHomeCities(mActivity: Activity) = AdapterHomeCities((mActivity as AppCompatActivity).lifecycleScope)

    @Provides
    @ActivityScoped
    fun provideAdapterHomeFoods(mActivity: Activity) = AdapterHomeFoods((mActivity as AppCompatActivity).lifecycleScope)

}