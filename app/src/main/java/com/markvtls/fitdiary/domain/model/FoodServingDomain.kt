package com.markvtls.fitdiary.domain.model

data class FoodServingDomain(
    val name: String,
    val protein: Double,
    val carbon: Double,
    val fat: Double,
    val date: String
)