package com.demo.japark.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.demo.japark.data.room.dao.FoodsDAO
import com.demo.japark.models.data.ModelJapanFood

@Database(entities = [ModelJapanFood::class], version = 1)
abstract class FoodsDatabase: RoomDatabase() {

    companion object{
        fun getInstance(mContext: Context) = Room.databaseBuilder(
            mContext,
            FoodsDatabase::class.java,
            "foods_database"
        ).build()
    }

    abstract fun foodsDao(): FoodsDAO

}