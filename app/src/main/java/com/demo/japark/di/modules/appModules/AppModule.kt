package com.demo.japark.di.modules.appModules

import android.content.Context
import com.demo.japark.data.AppSharedPrefs
import com.demo.japark.utils.DefaultExceptionHandler
import com.demo.japark.utils.InternetUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDefaultExceptionHandler(@ApplicationContext appContext: Context) = DefaultExceptionHandler(appContext)

    @Provides
    @Singleton
    fun provideInternetUtil(@ApplicationContext appContext: Context) = InternetUtil(appContext)

    @Provides
    @Singleton
    fun provideLogUtilTree() = Timber.DebugTree()                                                   // else We can also provide custom LogTree for Release Build

    @Provides
    @Singleton
    fun provideAppSharedPrefs(@ApplicationContext appContext: Context) = AppSharedPrefs.getInstance(appContext)

}