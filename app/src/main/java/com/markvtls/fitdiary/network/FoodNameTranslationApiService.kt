package com.markvtls.fitdiary.network

import com.markvtls.fitdiary.Constants
import com.markvtls.fitdiary.data.FoodNutrition
import com.squareup.moshi.Json
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://translate.api.cloud.yandex.net/translate/v2/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface FoodNameTranslationApiService {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${Constants.YANDEX_API_KEY}")
    @POST("translate")
    suspend fun translateName(@Body json: TranslationRequest): Response<TranslationResponse> //suspend
}

object TranslationApi {
    val retrofitService: FoodNameTranslationApiService by lazy {
        retrofit.create(FoodNameTranslationApiService::class.java)
    }
}