package com.markvtls.fitdiary.di

import com.markvtls.fitdiary.food.domain.repository.FoodServingRepository
import com.markvtls.fitdiary.pedometer.domain.repository.StepActivityRepository
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetLastInsertionDateUseCase
import com.markvtls.fitdiary.pedometer.domain.use_cases.GetStepsByDateUseCase
import com.markvtls.fitdiary.pedometer.domain.use_cases.InsertDailyStepsUseCase
import com.markvtls.fitdiary.food.domain.use_cases.DeleteFoodUseCase
import com.markvtls.fitdiary.food.domain.use_cases.*
import com.markvtls.fitdiary.profile.domain.repository.UserProfileRepository
import com.markvtls.fitdiary.profile.domain.use_cases.GetOverviewUseCase
import com.markvtls.fitdiary.profile.domain.use_cases.GetPreferencesUseCase
import com.markvtls.fitdiary.profile.domain.use_cases.GetSettingsUseCase
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

    @Singleton
    @Provides
    fun provideGetStepsByDateUseCase(repository: StepActivityRepository): GetStepsByDateUseCase {
        return GetStepsByDateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideInsertDailyStepsUseCase(repository: StepActivityRepository): InsertDailyStepsUseCase {
        return InsertDailyStepsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetLastInsertionDateUseCase(repository: StepActivityRepository): GetLastInsertionDateUseCase {
        return GetLastInsertionDateUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetPreferencesUseCase(repository: UserProfileRepository): GetPreferencesUseCase {
        return GetPreferencesUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetSettingsUseCase(repository: UserProfileRepository): GetSettingsUseCase {
        return GetSettingsUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideGetOverviewUseCase( foodRepository: FoodServingRepository, pedometerRepository: StepActivityRepository): GetOverviewUseCase {
        return GetOverviewUseCase(foodRepository, pedometerRepository)
    }
}