package com.samayteck.core.util

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable

fun Drawable.toBitmapSafe(
    width: Int,
    height: Int
): Bitmap {

    val bitmap =
        Bitmap.createBitmap(
            width,
            height,
            Bitmap.Config.ARGB_8888
        )

    val canvas = Canvas(bitmap)

    setBounds(
        0,
        0,
        width,
        height
    )

    draw(canvas)

    return bitmap
}