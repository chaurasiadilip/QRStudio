package com.samayteck.core.util

import android.graphics.Bitmap

object BitmapUtils {

    fun scale(
        bitmap: Bitmap,
        width: Int,
        height: Int
    ): Bitmap {

        return Bitmap.createScaledBitmap(
            bitmap,
            width,
            height,
            true
        )
    }
}