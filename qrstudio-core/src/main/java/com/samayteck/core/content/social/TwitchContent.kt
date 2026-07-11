package com.samayteck.core.content.social

import com.samayteck.core.content.QrContent

data class TwitchContent(
    val username: String
) : QrContent {
    override fun encode(): String {
        return "https://twitch.tv/$username"
    }
}
