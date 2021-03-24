package com.demo.japark.models.util

import androidx.annotation.ColorRes
import androidx.annotation.Keep
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.demo.japark.R
import com.demo.japark.models.BaseModel
import com.google.android.material.snackbar.Snackbar

@Keep
data class ModelSnackbarData(
    var msg: String,
    var coordinatorLayout: CoordinatorLayout,
    @ColorRes var bgColor: Int = R.color.colorBgSnackBar,
    @ColorRes var txtColor: Int = R.color.colorTextWhite,
    var length: Int = Snackbar.LENGTH_SHORT,
    var action: String? = null
) : BaseModel