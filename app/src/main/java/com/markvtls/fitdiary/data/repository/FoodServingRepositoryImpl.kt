package com.markvtls.fitdiary.data.repository

import com.markvtls.fitdiary.data.FoodServing
import com.markvtls.fitdiary.data.local.FoodServingDatabase
import com.markvtls.fitdiary.data.IdHolder
import com.markvtls.fitdiary.data.dto.FoodNutrition
import com.markvtls.fitdiary.data.dto.TranslationRequest
import com.markvtls.fitdiary.data.dto.TranslationResponse
import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import com.markvtls.fitdiary.data.network.FoodNameTranslationApiService
import com.markvtls.fitdiary.data.network.FoodNutritionApiService
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


}