package com.samayteck.core.content.contact

import com.samayteck.core.content.QrContent

data class MeCardContent(
    val name: String,
    val phone: String? = null,
    val email: String? = null,
    val address: String? = null,
    val url: String? = null,
    val note: String? = null
) : QrContent {

    override fun encode(): String {
        return buildString {
            append("MECARD:")
            append("N:$name;")
            if (!phone.isNullOrBlank()) append("TEL:$phone;")
            if (!email.isNullOrBlank()) append("EMAIL:$email;")
            if (!address.isNullOrBlank()) append("ADR:$address;")
            if (!url.isNullOrBlank()) append("URL:$url;")
            if (!note.isNullOrBlank()) append("NOTE:$note;")
            append(";")
        }
    }
}
