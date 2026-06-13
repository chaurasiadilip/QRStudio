package com.samayteck.core.content.social

import com.samayteck.core.content.QrContent

data class YouTubeContent(
    val channelId: String
) : QrContent {

    override fun encode(): String {
        return "https://youtube.com/$channelId"
    }
}
