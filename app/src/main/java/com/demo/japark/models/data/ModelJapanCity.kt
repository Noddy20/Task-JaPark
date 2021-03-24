package com.demo.japark.models.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import androidx.annotation.Keep
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.demo.japark.models.BaseModel
import com.demo.japark.models.getDiffUtil
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
@Keep
@Serializable
@Entity(tableName = "japan_cities", indices = [Index(value = ["name"], unique = true)])   //Using name as unique key because we don't have any unique Id
data class ModelJapanCity(
    @ColumnInfo(name = "name") @SerialName("name") val name: String?,
    @ColumnInfo(name = "image") @SerialName("image") val image: String?,
    @ColumnInfo(name = "description") @SerialName("description") val description: String?
) : Parcelable, BaseModel {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object{

        /**
         *   Diff Util comparing contents by object toString() because we don't have any kind of id's to compare
         */

        val DIFF_UTIL = getDiffUtil<ModelJapanCity> { oldItem, newItem ->
            return@getDiffUtil oldItem.toString() == newItem.toString()
        }

    }

}