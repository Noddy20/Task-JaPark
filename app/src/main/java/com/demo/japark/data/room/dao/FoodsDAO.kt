package com.demo.japark.data.room.dao

import androidx.room.*
import com.demo.japark.models.data.ModelJapanFood

@Dao
interface FoodsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoods(foods: List<ModelJapanFood>)

    @Update
    suspend fun updateFoods(foods: List<ModelJapanFood>)

    @Query("DELETE FROM japan_foods")
    suspend fun nukeTable()

    @Query("SELECT * FROM japan_foods")
    suspend fun loadAllFoods(): List<ModelJapanFood>

}