package com.markvtls.fitdiary.food.data.source.network

import com.markvtls.fitdiary.food.data.dto.TranslationRequest
import com.markvtls.fitdiary.food.data.dto.TranslationResponse
import com.markvtls.fitdiary.utils.Constants
import retrofit2.http.*



interface FoodNameTranslationApiService {

    @Headers(
        "Content-Type: application/json",
        "Authorization: Bearer ${Constants.YANDEX_API_KEY}")
    @POST("translate")
    suspend fun translateName(@Body json: TranslationRequest): TranslationResponse //suspend
}

