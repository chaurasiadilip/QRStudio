package com.samayteck.core.content.social

import com.samayteck.core.content.QrContent

data class InstagramContent(
    val username: String
) : QrContent {

    override fun encode(): String {
        return "https://instagram.com/$username"
    }
}
