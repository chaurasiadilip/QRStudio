package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent
import java.net.URLEncoder

data class UpiContent(
    val payeeAddress: String,
    val payeeName: String,
    val amount: String? = null,
    val transactionNote: String? = null,
    val merchantCode: String? = null,
    val currency: String = "INR"
) : QrContent {

    override fun encode(): String {
        return buildString {
            append("upi://pay?")
            append("pa=${payeeAddress}")
            append("&pn=${URLEncoder.encode(payeeName, "UTF-8")}")
            if (!amount.isNullOrBlank()) {
                append("&am=${amount}")
            }
            if (!transactionNote.isNullOrBlank()) {
                append("&tn=${URLEncoder.encode(transactionNote, "UTF-8")}")
            }
            if (!merchantCode.isNullOrBlank()) {
                append("&mc=${merchantCode}")
            }
            append("&cu=${currency}")
        }
    }
}
