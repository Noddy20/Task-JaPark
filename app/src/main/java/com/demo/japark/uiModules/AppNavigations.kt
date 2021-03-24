package com.demo.japark.uiModules

import android.content.Context
import android.content.Intent
import com.demo.japark.uiModules.home.ActivityHome

fun Context.gotoHomeActivity(){
    startActivity(Intent(this, ActivityHome::class.java))
}