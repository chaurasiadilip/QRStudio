package com.samayteck.core.content.social

import com.samayteck.core.content.QrContent

data class TelegramContent(

    val username: String

) : QrContent {

    override fun encode(): String {

        return "https://t.me/$username"
    }
}
