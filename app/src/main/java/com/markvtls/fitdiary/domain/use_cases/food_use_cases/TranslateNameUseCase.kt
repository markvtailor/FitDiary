package com.markvtls.fitdiary.domain.use_cases.food_use_cases

import com.markvtls.fitdiary.data.dto.TranslationRequest
import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TranslateNameUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    operator fun invoke(json: TranslationRequest): Flow<String> = flow {
        val translation = repository.translateName(json).translations.first().text
        emit(translation)
    }
}