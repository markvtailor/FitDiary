package com.markvtls.fitdiary.di

import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import com.markvtls.fitdiary.domain.use_cases.food_use_cases.DeleteFoodUseCase
import com.markvtls.fitdiary.domain.use_cases.food_use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideTranslateNameUseCase(repository: FoodServingRepository): TranslateNameUseCase {
        return TranslateNameUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetFoodNutritionUseCase(repository: FoodServingRepository): GetFoodNutritionUseCase {
        return GetFoodNutritionUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideInsertFoodUseCase(repository: FoodServingRepository): InsertFoodUseCase {
        return InsertFoodUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideDeleteFoodUseCase(repository: FoodServingRepository): DeleteFoodUseCase {
        return DeleteFoodUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetCompleteFoodListUseCase(repository: FoodServingRepository): GetCompleteFoodListUseCase {
        return GetCompleteFoodListUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetFoodListByDateUseCase(repository: FoodServingRepository): GetFoodListByDateUseCase {
        return GetFoodListByDateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetOneFoodByIdUseCase(repository: FoodServingRepository): GetOneFoodByIdUseCase {
        return GetOneFoodByIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetCaloriesUseCase(repository: FoodServingRepository): GetCaloriesUseCase {
        return GetCaloriesUseCase(repository)
    }
}