package com.samayteck.core.content.basic

import com.samayteck.core.content.QrContent

data class EmailContent(
    val email: String,
    val subject: String = "",
    val body: String = ""
) : QrContent {

    override fun encode(): String {

        return buildString {

            append("mailto:")
            append(email)

            if (
                subject.isNotBlank() ||
                body.isNotBlank()
            ) {

                append("?")

                append(
                    "subject=$subject"
                )

                append("&body=$body")
            }
        }
    }
}