package com.markvtls.fitdiary.di

import android.content.Context
import com.markvtls.fitdiary.food.data.source.local.FoodServingDao
import com.markvtls.fitdiary.food.data.source.local.FoodServingDatabase
import com.markvtls.fitdiary.pedometer.data.source.local.StepActivityDao
import com.markvtls.fitdiary.pedometer.data.source.local.StepActivityDatabase
import com.markvtls.fitdiary.food.data.repository.FoodServingRepositoryImpl
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import com.markvtls.fitdiary.food.data.source.network.FoodNameTranslationApiService
import com.markvtls.fitdiary.food.data.source.network.FoodNutritionApiService
import com.markvtls.fitdiary.pedometer.data.repository.StepActivityRepositoryImpl
import com.markvtls.fitdiary.pedometer.domain.repository.StepActivityRepository
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

    @Provides
    fun provideStepActivityDao(database: StepActivityDatabase): StepActivityDao {
        return database.stepActivityDao()
    }

    @Provides
    @Singleton
    fun provideStepActivityDatabase(@ApplicationContext context: Context): StepActivityDatabase {
        return StepActivityDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideStepActivityRepository(@ApplicationContext context: Context, database: StepActivityDatabase): StepActivityRepository {
        return StepActivityRepositoryImpl(context, database)
    }
}