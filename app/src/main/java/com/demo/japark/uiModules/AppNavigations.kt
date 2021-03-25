package com.demo.japark.uiModules

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.demo.japark.models.data.ModelJapanCity
import com.demo.japark.uiModules.cityDetails.ActivityCity
import com.demo.japark.uiModules.home.ActivityHome
import com.demo.japark.uiModules.imagePopup.ActivityImagePopup

fun Context.gotoHomeActivity(){
    startActivity(Intent(this, ActivityHome::class.java))
}

fun Context.gotoCityDetailsActivity(model: ModelJapanCity, options: Bundle?){
    startActivity(ActivityCity.getIntent(this, model), options)
}

fun Context.gotoImagePopupActivity(title: String?, imgUrl: String?, options: Bundle?){
    startActivity(ActivityImagePopup.getIntent(this, title, imgUrl), options)
}