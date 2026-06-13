package com.samayteck.compose

import android.graphics.Bitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter

fun Bitmap.toStyledQrPainter(): Painter {

    return BitmapPainter(
        image = asImageBitmap()
    )
}