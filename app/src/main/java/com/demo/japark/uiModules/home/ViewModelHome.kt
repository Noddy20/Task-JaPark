package com.demo.japark.uiModules.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.demo.japark.uiModules.base.BaseViewModel
import com.demo.japark.utils.extFunctions.launchWithExcHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ViewModelHome @Inject constructor(
    private val mRepoHome: RepoHome): BaseViewModel() {

    private val paramsMainDataMLD = MutableLiveData<Unit>()
    fun callMainDataApi(){
        paramsMainDataMLD.value = Unit
    }

    val mainDataLD = paramsMainDataMLD.switchMap { mRepoHome.getMainResponse((ioCorContext)) }



    fun clearPersistedData(){
        viewModelScope.launchWithExcHandler {
            mRepoHome.clearPersistedDb()
        }
    }

}