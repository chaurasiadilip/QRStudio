package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent

data class VideoContent(
    val videoUrl: String
) : QrContent {

    override fun encode(): String = videoUrl
}
