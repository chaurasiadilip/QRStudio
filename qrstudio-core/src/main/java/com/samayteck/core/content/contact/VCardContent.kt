package com.samayteck.core.content.contact

import com.samayteck.core.content.QrContent

data class VCardContent(

    val firstName: String,

    val lastName: String,

    val phone: String,

    val email: String
) : QrContent {

    override fun encode(): String {

        return """
            BEGIN:VCARD
            VERSION:3.0
            N:$lastName;$firstName
            TEL:$phone
            EMAIL:$email
            END:VCARD
        """.trimIndent()
    }
}