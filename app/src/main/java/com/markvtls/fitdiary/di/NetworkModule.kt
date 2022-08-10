package com.markvtls.fitdiary.di

import com.markvtls.fitdiary.utils.Constants
import com.markvtls.fitdiary.food.data.source.network.FoodNameTranslationApiService
import com.markvtls.fitdiary.food.data.source.network.FoodNutritionApiService
import com.markvtls.fitdiary.utils.NutritionRetrofitClient
import com.markvtls.fitdiary.utils.TranslationRetrofitClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @TranslationRetrofitClient
    @Singleton
    @Provides
    fun provideTranslationRetrofitClient(moshi: Moshi): FoodNameTranslationApiService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.TRANSLATION_BASE_URL)
            .build()
            .create(FoodNameTranslationApiService::class.java)
    }

    @NutritionRetrofitClient
    @Singleton
    @Provides
    fun provideNutritionRetrofitClient(moshi: Moshi): FoodNutritionApiService {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(Constants.NUTRITION_BASE_URL)
            .build()
            .create(FoodNutritionApiService::class.java)
    }



}