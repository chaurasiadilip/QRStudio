package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent

data class PdfContent(
    val url: String
) : QrContent {

    override fun encode(): String = url
}
