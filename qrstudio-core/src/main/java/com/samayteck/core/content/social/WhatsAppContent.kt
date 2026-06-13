package com.samayteck.core.content.social

import com.samayteck.core.content.QrContent

data class WhatsAppContent(
    val phone: String,
    val message: String
) : QrContent {

    override fun encode(): String {
        return buildString {
            append(
                "https://wa.me/"
            )
            append(phone)
            append(
                "?text="
            )
            append(message)
        }
    }
}
