package com.markvtls.fitdiary.food.data.source.network

import com.markvtls.fitdiary.food.data.dto.FoodNutrition
import com.markvtls.fitdiary.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface FoodNutritionApiService {

    @Headers("X-Api-Key: ${Constants.API_KEY}")
    @GET("nutrition")
    suspend fun getNutritionInfo(@Query("query") foodName: String): List<FoodNutrition>
}

