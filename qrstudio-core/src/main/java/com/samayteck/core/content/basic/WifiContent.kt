package com.samayteck.core.content.basic

import com.samayteck.core.content.QrContent

data class WifiContent(

    val ssid: String,

    val password: String,

    val security: Security =
        Security.WPA

) : QrContent {

    enum class Security {
        WPA,
        WEP,
        NONE
    }

    override fun encode(): String {

        return "WIFI:T:${security.name};S:$ssid;P:$password;;"
    }
}