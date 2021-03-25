package com.demo.japark.uiModules.dialogs

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatDelegate
import com.demo.japark.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import timber.log.Timber
import javax.inject.Inject

/**
 *    A class to manage all the Dialogs of App
 */

class AppDialogs @Inject constructor(private val mActivity: Activity) {

    companion object{
    }

    private var mDialog: Dialog? = null

    private fun show() {
        mDialog?.runCatching {
            if (!mActivity.isFinishing) show()
        }
    }

    fun dismiss() {
        mDialog?.runCatching {
            if (isShowing) dismiss()
            mDialog = null
        }
    }


    fun showCountDownSelectDialog(mSelCountDownPos: Int, onSelect: (Int) -> Unit){
        var selCountDownPos = when(mSelCountDownPos){
            AppCompatDelegate.MODE_NIGHT_NO -> 1
            AppCompatDelegate.MODE_NIGHT_YES -> 2
            else -> 0
        }
        mDialog = MaterialAlertDialogBuilder(mActivity)
            .setTitle(mActivity.getString(R.string.title_select_theme))
            .setSingleChoiceItems(R.array.arr_themes_arr, selCountDownPos){ _, which ->
                Timber.d("showCountDownSelectDialog Selected Pos -> $which")
                selCountDownPos = which
            }
            .setPositiveButton(R.string.action_select){ _, _ ->
                val selTheme = when(selCountDownPos) {
                    1 -> AppCompatDelegate.MODE_NIGHT_NO
                    2 -> AppCompatDelegate.MODE_NIGHT_YES
                    else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                }
                onSelect(selTheme)
                dismiss()
            }
            .setNegativeButton(R.string.action_cancel){ _, _ ->
                dismiss()
            }.create()

        show()
    }

}