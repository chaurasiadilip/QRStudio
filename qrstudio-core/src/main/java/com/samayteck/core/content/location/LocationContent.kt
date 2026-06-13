package com.samayteck.core.content.location

import com.samayteck.core.content.QrContent

data class LocationContent(

    val latitude: Double,

    val longitude: Double

) : QrContent {

    override fun encode(): String {

        return "geo:$latitude,$longitude"
    }
}