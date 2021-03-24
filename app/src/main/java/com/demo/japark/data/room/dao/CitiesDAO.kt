package com.demo.japark.data.room.dao

import androidx.room.*
import com.demo.japark.models.data.ModelJapanCity

@Dao
interface CitiesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCities(cities: List<ModelJapanCity>)

    @Update
    suspend fun updateCities(cities: List<ModelJapanCity>)

    @Query("DELETE FROM japan_cities")
    suspend fun nukeTable()

    @Query("SELECT * FROM japan_cities")
    suspend fun loadAllCities(): List<ModelJapanCity>

}