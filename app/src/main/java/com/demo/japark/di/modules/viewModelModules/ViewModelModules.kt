package com.demo.japark.di.modules.viewModelModules

import android.content.Context
import com.demo.japark.data.room.CitiesDatabase
import com.demo.japark.data.room.FoodsDatabase
import com.demo.japark.network.RetrofitApiClient
import com.demo.japark.uiModules.home.RepoHome
import com.demo.japark.utils.InternetUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object ViewModelModules {

    @Provides
    @ActivityRetainedScoped
    fun provideRepoHome(@ApplicationContext mAppContext: Context,
                                       mInternetUtil: InternetUtil,
                                       mRetrofitClient: RetrofitApiClient,
                                       mCitiesDb: CitiesDatabase,
                                       mFoodsDb: FoodsDatabase
    ) = RepoHome(mAppContext, mInternetUtil, mRetrofitClient, mCitiesDb, mFoodsDb)

}
