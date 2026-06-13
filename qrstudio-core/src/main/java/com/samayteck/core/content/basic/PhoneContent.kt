package com.samayteck.core.content.basic

import com.samayteck.core.content.QrContent

data class PhoneContent(
    val number: String
) : QrContent {

    override fun encode(): String =
        "tel:$number"
}