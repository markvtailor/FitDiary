package com.markvtls.fitdiary.food.data.repository

import com.markvtls.fitdiary.food.data.source.local.FoodServing
import com.markvtls.fitdiary.food.data.source.local.FoodServingDatabase
import com.markvtls.fitdiary.food.data.source.local.IdHolder
import com.markvtls.fitdiary.food.data.dto.FoodNutrition
import com.markvtls.fitdiary.food.data.dto.TranslationRequest
import com.markvtls.fitdiary.food.data.dto.TranslationResponse
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import com.markvtls.fitdiary.food.data.source.network.FoodNameTranslationApiService
import com.markvtls.fitdiary.food.data.source.network.FoodNutritionApiService
import com.markvtls.fitdiary.food.domain.model.CaloriesForDay
import com.markvtls.fitdiary.utils.NutritionRetrofitClient
import com.markvtls.fitdiary.utils.TranslationRetrofitClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodServingRepositoryImpl @Inject constructor(
    private val database: FoodServingDatabase,
    @TranslationRetrofitClient private val TranslationApi: FoodNameTranslationApiService,
    @NutritionRetrofitClient private val NutritionApi: FoodNutritionApiService
    ): FoodServingRepository {

    override suspend fun insertFood(foodServing: FoodServing) { //done
        database.foodServingDao().insert(foodServing)
    }

    override suspend fun deleteFood(id: Int) { //done
        database.foodServingDao().deleteById(IdHolder(id))
    }

    override fun getAll(): Flow<List<FoodServing>> = database.foodServingDao().getAll() //done

    override fun getFoodById(id: Int): Flow<FoodServing> { //done
       return database.foodServingDao().getOneById(id)
    }

    override fun getFoodByDate(date: String): Flow<List<FoodServing>> { //date
        return database.foodServingDao().getAllByDate(date)
    }
    override fun getCcalSum(date: String): Flow<Int> { //done
       return database.foodServingDao().getTotalCcalByDate(date)
    }

    override suspend fun translateName(json: TranslationRequest): TranslationResponse { //done
        return TranslationApi.translateName(json)
    }

    override suspend fun getNutrition(name: String): List<FoodNutrition> { //done
        return NutritionApi.getNutritionInfo(name)
    }

    override fun getAllForCurrentWeek(dates: List<String>): Flow<List<CaloriesForDay>> {
        return database.foodServingDao().getAllForCurrentWeek(dates)
    }


}