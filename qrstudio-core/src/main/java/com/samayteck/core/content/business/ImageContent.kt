package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent

data class ImageContent(
    val imageUrl: String
) : QrContent {

    override fun encode(): String = imageUrl
}
