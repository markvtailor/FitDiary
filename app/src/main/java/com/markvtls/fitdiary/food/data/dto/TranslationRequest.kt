package com.markvtls.fitdiary.food.data.dto

import com.markvtls.fitdiary.utils.Constants

class TranslationRequest (
    val folderId: String = Constants.YANDEX_FOLDER_ID,
    var texts: String = "",
    val targetLanguageCode: String = Constants.TARGET_LANGUAGE
)