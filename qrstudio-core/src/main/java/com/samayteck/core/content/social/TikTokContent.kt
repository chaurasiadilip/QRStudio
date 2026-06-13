package com.samayteck.core.content.social

import com.samayteck.core.content.QrContent

data class TikTokContent(
    val username: String
) : QrContent {

    override fun encode(): String {
        return "https://www.tiktok.com/@$username"
    }
}
