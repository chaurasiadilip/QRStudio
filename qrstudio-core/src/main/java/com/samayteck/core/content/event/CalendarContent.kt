package com.samayteck.core.content.event

import com.samayteck.core.content.QrContent

data class CalendarContent(
    val title: String,
    val start: String,
    val end: String
) : QrContent {

    override fun encode(): String {
        return """
                    BEGIN:VEVENT
                    SUMMARY:$title
                    DTSTART:$start
                    DTEND:$end
                    END:VEVENT
                """.trimIndent()
    }
}