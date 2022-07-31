package com.markvtls.fitdiary.network

import com.markvtls.fitdiary.Constants
import com.markvtls.fitdiary.data.FoodNutrition
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://api.api-ninjas.com/v1/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface FoodNutritionApiService {

    @Headers("X-Api-Key: ${Constants.API_KEY}")
    @GET("nutrition")
     suspend fun getNutritionInfo(@Query("query") foodName: String): List<FoodNutrition> //suspend
}

object NutritionApi {
    val retrofitService: FoodNutritionApiService by lazy {
        retrofit.create(FoodNutritionApiService::class.java)
    }
}