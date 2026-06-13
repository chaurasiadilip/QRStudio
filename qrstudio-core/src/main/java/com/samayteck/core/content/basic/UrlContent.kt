package com.samayteck.core.content.basic

import com.samayteck.core.content.QrContent

data class UrlContent(
    val url: String
) : QrContent {

    override fun encode(): String =
        url
}