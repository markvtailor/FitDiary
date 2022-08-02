package com.markvtls.fitdiary.domain.use_cases.food_use_cases

import com.markvtls.fitdiary.data.dto.FoodNutrition
import com.markvtls.fitdiary.data.FoodServing
import com.markvtls.fitdiary.domain.repository.FoodServingRepository
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class InsertFoodUseCase @Inject constructor(
    private val repository: FoodServingRepository
) {
    suspend operator fun invoke(id: Int, name: String, amount: Int, nutrition: List<FoodNutrition>, date: String) {
        val food = FoodServing(id, name, amount, 0.0, 0.0, 0.0, 0.0, date)
        nutrition.forEach {
            food.ccal += it.calories.toDouble()
            food.protein += it.protein_g.toDouble()
            food.carbon += it.carbohydrates_total_g.toDouble()
            food.fat += it.fat_total_g.toDouble()
        }
        food.apply {
            ccal *= amount * 0.01
            protein *= amount * 0.01
            carbon *= amount * 0.01
            fat *= amount * 0.01
        }
        val formatter =
            NumberFormat.getInstance(Locale.US) ///How to deal with numbers like 1,234,400.5?
        food.apply {
            ccal = formatter.format(ccal).replace(",", "").toDouble()
            protein = formatter.format(protein).replace(",", "").toDouble()
            carbon = formatter.format(carbon).replace(",", "").toDouble()
            fat = formatter.format(fat).replace(",", "").toDouble()
        }
        repository.insertFood(food)
    }
}