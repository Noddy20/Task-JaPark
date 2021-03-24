package com.demo.japark.uiModules.base

import android.content.Context
import androidx.annotation.StringRes

abstract class BaseRepo(protected val mAppContext: Context) {

    protected fun getString(@StringRes resId: Int) = mAppContext.getString(resId)


}