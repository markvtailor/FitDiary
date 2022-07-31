package com.markvtls.fitdiary.network

import com.markvtls.fitdiary.Constants
import com.squareup.moshi.Moshi


class TranslationRequest (
    val folderId: String = Constants.YANDEX_FOLDER_ID,
    var texts: String = "",
    val targetLanguageCode: String = Constants.TARGET_LANGUAGE
)

data class TranslationResponse (
    val translations: List<Translations>
        )

data class Translations (
    val text: String,
    val detectedLanguageCode: String
)

class Post {
    private val title: String? = null
    private val author: String? = null
    private val text: String? = null // constructor, getters and setters
}

