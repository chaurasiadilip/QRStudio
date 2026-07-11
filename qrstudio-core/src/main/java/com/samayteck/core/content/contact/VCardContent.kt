package com.samayteck.core.content.contact

import com.samayteck.core.content.QrContent

data class VCardContent(
    val firstName: String,
    val lastName: String,
    val organization: String? = null,
    val title: String? = null,
    val phone: String? = null,
    val mobile: String? = null,
    val email: String? = null,
    val url: String? = null,
    val street: String? = null,
    val city: String? = null,
    val zip: String? = null,
    val state: String? = null,
    val country: String? = null
) : QrContent {

    override fun encode(): String {
        return buildString {
            append("BEGIN:VCARD\n")
            append("VERSION:3.0\n")
            append("N:$lastName;$firstName\n")
            append("FN:$firstName $lastName\n")
            if (!organization.isNullOrBlank()) append("ORG:$organization\n")
            if (!title.isNullOrBlank()) append("TITLE:$title\n")
            if (!phone.isNullOrBlank()) append("TEL;TYPE=WORK,VOICE:$phone\n")
            if (!mobile.isNullOrBlank()) append("TEL;TYPE=CELL,VOICE:$mobile\n")
            if (!email.isNullOrBlank()) append("EMAIL:$email\n")
            if (!url.isNullOrBlank()) append("URL:$url\n")
            if (!street.isNullOrBlank() || !city.isNullOrBlank()) {
                append("ADR;TYPE=WORK:;;$street;$city;$state;$zip;$country\n")
            }
            append("END:VCARD")
        }
    }
}
