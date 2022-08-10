package com.markvtls.fitdiary.food.data.source.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.markvtls.fitdiary.food.domain.model.FoodServingDomain

@Entity
data class FoodServing (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    @ColumnInfo(name = "amount_in_grams")val amount: Int,
    @ColumnInfo(name = "calories") var ccal: Double,
    var protein: Double,
    var carbon: Double,
    var fat: Double,
    val date: String
    )

data class IdHolder (val id: Int)

fun FoodServing.toDomain(): FoodServingDomain {
    return FoodServingDomain(
        name = name,
        protein = protein,
        carbon = carbon,
        fat = fat,
        date = date
    )
}