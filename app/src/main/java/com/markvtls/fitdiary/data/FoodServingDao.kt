package com.markvtls.fitdiary.data

import androidx.room.*
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
    @Update
    suspend fun update(foodServing: FoodServing)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(foodServing: FoodServing)

    @Delete(entity = FoodServing::class)
    suspend fun deleteById(vararg id: IdHolder)
}