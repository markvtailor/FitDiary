package com.markvtls.fitdiary.di

import android.content.Context
import com.markvtls.fitdiary.food.data.repository.FoodServingRepositoryImpl
import com.markvtls.fitdiary.food.data.source.local.FoodServingDao
import com.markvtls.fitdiary.food.data.source.local.FoodServingDatabase
import com.markvtls.fitdiary.food.data.source.network.FoodNameTranslationApiService
import com.markvtls.fitdiary.food.data.source.network.FoodNutritionApiService
import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import com.markvtls.fitdiary.pedometer.data.repository.PedometerRepositoryImpl
import com.markvtls.fitdiary.pedometer.data.source.local.PedometerDao
import com.markvtls.fitdiary.pedometer.data.source.local.PedometerDatabase
import com.markvtls.fitdiary.pedometer.domain.repository.PedometerRepository
import com.markvtls.fitdiary.profile.data.SettingsDataStore
import com.markvtls.fitdiary.profile.data.repository.UserProfileRepositoryImpl
import com.markvtls.fitdiary.profile.domain.repository.UserProfileRepository
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
    fun provideFoodServingRepository(
        database: FoodServingDatabase,
        @TranslationRetrofitClient TranslationApi: FoodNameTranslationApiService,
        @NutritionRetrofitClient NutritionApi: FoodNutritionApiService
    ): FoodServingRepository {
        return FoodServingRepositoryImpl(database, TranslationApi, NutritionApi)
    }

    @Provides
    fun provideStepActivityDao(database: PedometerDatabase): PedometerDao {
        return database.stepActivityDao()
    }

    @Provides
    @Singleton
    fun provideStepActivityDatabase(@ApplicationContext context: Context): PedometerDatabase {
        return PedometerDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideStepActivityRepository(database: PedometerDatabase): PedometerRepository {
        return PedometerRepositoryImpl(database)
    }

    @Provides
    @Singleton
    fun provideSettingStore(@ApplicationContext context: Context): SettingsDataStore {
        return SettingsDataStore(context)
    }

    @Provides
    @Singleton
    fun provideUserProfileRepository(settingsDataStore: SettingsDataStore): UserProfileRepository {
        return UserProfileRepositoryImpl(settingsDataStore)
    }
}