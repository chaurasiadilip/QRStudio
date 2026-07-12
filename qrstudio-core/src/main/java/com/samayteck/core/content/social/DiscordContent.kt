package com.samayteck.core.content.social

import com.samayteck.core.content.QrContent

data class DiscordContent(
    val inviteCode: String
) : QrContent {
    override fun encode(): String {
        return "https://discord.gg/$inviteCode"
    }
}
