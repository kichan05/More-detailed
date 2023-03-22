package com.heechan.moredetailed.model.data

import com.squareup.moshi.Json

data class Result(
    val srcLangType: String,
    val tarLangType: String,
    val translatedText: String
)