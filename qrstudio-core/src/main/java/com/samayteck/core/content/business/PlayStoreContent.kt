package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent

data class PlayStoreContent(
    val packageName: String
) : QrContent {

    override fun encode(): String {
        return "https://play.google.com/store/apps/details?id=$packageName"
    }
}
