package com.markvtls.fitdiary.di

import android.content.Context
import com.markvtls.fitdiary.data.local.FoodServingDao
import com.markvtls.fitdiary.data.local.FoodServingDatabase
import com.markvtls.fitdiary.data.repository.FoodServingRepositoryImpl
import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import com.markvtls.fitdiary.data.network.FoodNameTranslationApiService
import com.markvtls.fitdiary.data.network.FoodNutritionApiService
import com.markvtls.fitdiary.utils.NutritionRetrofitClient
import com.markvtls.fitdiary.utils.TranslationRetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideFoodServingDao(database: FoodServingDatabase): FoodServingDao {
        return database.foodServingDao()
    }

    @Provides
    @Singleton
    fun provideFoodServingDatabase(@ApplicationContext context: Context): FoodServingDatabase {
        return FoodServingDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideFoodServingRepository(database: FoodServingDatabase, @TranslationRetrofitClient TranslationApi: FoodNameTranslationApiService, @NutritionRetrofitClient NutritionApi: FoodNutritionApiService): FoodServingRepository {
        return FoodServingRepositoryImpl(database, TranslationApi, NutritionApi)
    }
}