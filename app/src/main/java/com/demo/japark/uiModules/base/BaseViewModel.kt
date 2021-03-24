package com.demo.japark.uiModules.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.japark.utils.DefaultExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus

abstract class BaseViewModel: ViewModel() {

    //ViewModel LifecycleScope + Coroutine IO dispatcher + unexpected exception handler
    protected val ioCorContext = (viewModelScope + Dispatchers.IO + DefaultExceptionHandler.coroutineExceptionHandler).coroutineContext

}