package com.samayteck.core.content.basic

import com.samayteck.core.content.QrContent

data class SmsContent(
    val number: String,
    val message: String
) : QrContent {

    override fun encode(): String =
        "smsto:$number:$message"
}