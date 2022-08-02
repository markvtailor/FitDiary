package com.markvtls.fitdiary.data.network

import com.markvtls.fitdiary.utils.Constants
import com.markvtls.fitdiary.data.dto.FoodNutrition
import retrofit2.http.*



interface FoodNutritionApiService {

    @Headers("X-Api-Key: ${Constants.API_KEY}")
    @GET("nutrition")
     suspend fun getNutritionInfo(@Query("query") foodName: String): List<FoodNutrition> //suspend
}

