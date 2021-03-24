package com.demo.japark.di.modules.appModules

import android.content.Context
import com.demo.japark.data.room.CitiesDatabase
import com.demo.japark.data.room.FoodsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCitiesDatabase(@ApplicationContext mAppContext: Context): CitiesDatabase = CitiesDatabase.getInstance(mAppContext)

    @Provides
    @Singleton
    fun provideFoodsDatabase(@ApplicationContext mAppContext: Context): FoodsDatabase = FoodsDatabase.getInstance(mAppContext)

}