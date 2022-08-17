package com.markvtls.fitdiary.pedometer.domain.model

data class StepActivityDomain(
    val date: String,
    val steps: Int,
    val calories: Int
)