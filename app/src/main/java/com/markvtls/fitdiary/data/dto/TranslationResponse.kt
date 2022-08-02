package com.markvtls.fitdiary.data.dto

data class TranslationResponse (
    val translations: List<Translations>
)

data class Translations (
    val text: String,
    val detectedLanguageCode: String
)