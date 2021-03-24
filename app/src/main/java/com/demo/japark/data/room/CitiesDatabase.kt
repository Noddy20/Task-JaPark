package com.demo.japark.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.japark.data.room.dao.CitiesDAO
import com.demo.japark.models.data.ModelJapanCity

@Database(entities = [ModelJapanCity::class], version = 1)
abstract class CitiesDatabase: RoomDatabase() {

    companion object{
        fun getInstance(mContext: Context) = Room.databaseBuilder(
            mContext,
            CitiesDatabase::class.java,
            "cities_database"
        ).build()
    }

    abstract fun citiesDao(): CitiesDAO

}