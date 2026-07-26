package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent

data class SolanaContent(
    val address: String,
    val amount: String? = null,
    val label: String? = null,
    val message: String? = null
) : QrContent {
    override fun encode(): String {
        return buildString {
            append("solana:$address")
            val params = mutableListOf<String>()
            if (!amount.isNullOrBlank()) params.add("amount=$amount")
            if (!label.isNullOrBlank()) params.add("label=$label")
            if (!message.isNullOrBlank()) params.add("message=$message")
            
            if (params.isNotEmpty()) {
                append("?")
                append(params.joinToString("&"))
            }
        }
    }
}
