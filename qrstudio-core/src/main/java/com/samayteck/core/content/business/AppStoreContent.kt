package com.samayteck.core.content.business

import com.samayteck.core.content.QrContent

data class AppStoreContent(
    val appId: String
) : QrContent {

    override fun encode(): String {
        return "https://apps.apple.com/app/id$appId"
    }
}
