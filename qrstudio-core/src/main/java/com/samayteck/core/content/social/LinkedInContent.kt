package com.samayteck.core.content.social

import com.samayteck.core.content.QrContent

data class LinkedInContent(
    val profileId: String
) : QrContent {

    override fun encode(): String {
        return "https://linkedin.com/in/$profileId"
    }
}
