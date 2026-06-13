package com.samayteck.core.content.basic

import com.samayteck.core.content.QrContent

data class TextContent(
    val text: String
) : QrContent {

    override fun encode(): String = text
}
