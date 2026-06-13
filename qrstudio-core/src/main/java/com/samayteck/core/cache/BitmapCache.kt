package com.samayteck.core.cache

import android.graphics.Bitmap

interface BitmapCache {

    fun put(
        key: String,
        bitmap: Bitmap
    )

    fun get(
        key: String
    ): Bitmap?

    fun clear()
}