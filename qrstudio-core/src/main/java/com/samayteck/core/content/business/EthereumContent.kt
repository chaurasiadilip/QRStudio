package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent

data class EthereumContent(
    val address: String,
    val amount: String? = null,
    val gas: String? = null
) : QrContent {
    override fun encode(): String {
        return buildString {
            append("ethereum:$address")
            if (!amount.isNullOrBlank() || !gas.isNullOrBlank()) {
                append("?")
                if (!amount.isNullOrBlank()) append("value=$amount")
                if (!amount.isNullOrBlank() && !gas.isNullOrBlank()) append("&")
                if (!gas.isNullOrBlank()) append("gas=$gas")
            }
        }
    }
}
