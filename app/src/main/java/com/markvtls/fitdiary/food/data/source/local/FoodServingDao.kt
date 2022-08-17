package com.markvtls.fitdiary.food.data.source.local

import androidx.room.*
import com.markvtls.fitdiary.food.domain.model.CaloriesForDay
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodServingDao {

    @Query("SELECT * from foodserving ORDER BY id ASC")
    fun getAll(): Flow<List<FoodServing>>

    @Query("SELECT * from foodserving WHERE date = :date")
    fun getAllByDate(date: String): Flow<List<FoodServing>>

    @Query("SELECT * from foodserving WHERE id = :id ORDER BY id ASC")
    fun getOneById(id: Int): Flow<FoodServing>

    @Query("SELECT SUM(calories) from foodserving WHERE date =:date ")
    fun getTotalCcalByDate(date: String): Flow<Int>

    @Query("SELECT SUM(calories) as ccal, date from foodserving WHERE date IN (:dates) GROUP BY date")
    fun getAllForCurrentWeek(dates: List<String>): Flow<List<CaloriesForDay>>

    @Update
    suspend fun update(foodServing: FoodServing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodServing: FoodServing)

    @Delete(entity = FoodServing::class)
    suspend fun deleteById(vararg id: IdHolder)
}