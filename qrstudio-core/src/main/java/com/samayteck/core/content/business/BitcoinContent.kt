package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent

data class BitcoinContent(
    val address: String,
    val amount: String? = null,
    val message: String? = null
) : QrContent {

    override fun encode(): String {
        return buildString {
            append("bitcoin:$address")
            if (!amount.isNullOrBlank() || !message.isNullOrBlank()) {
                append("?")
                if (!amount.isNullOrBlank()) append("amount=$amount")
                if (!amount.isNullOrBlank() && !message.isNullOrBlank()) append("&")
                if (!message.isNullOrBlank()) append("message=$message")
            }
        }
    }
}
