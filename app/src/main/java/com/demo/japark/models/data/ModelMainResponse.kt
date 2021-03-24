package com.demo.japark.models.data

import android.os.Parcelable
import androidx.annotation.Keep
import com.demo.japark.models.BaseModel
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
@Keep
@Serializable
data class ModelMainResponse(
    @SerialName("cities") val cities: List<ModelJapanCity>?,
    @SerialName("foods") val foods: List<ModelJapanFood>?
) : Parcelable, BaseModel