package com.samayteck.renderer.logo

import android.graphics.Bitmap
import android.graphics.Canvas
import com.samayteck.core.renderer.logo.LogoRenderer

class SvgLogoRenderer(
    private val bitmap: Bitmap,
    private val logoPercent: Float
) : LogoRenderer {

    override fun draw(
        canvas: Canvas,
        qrSize: Int
    ) {

        val size =
            (qrSize * logoPercent)
                .toInt()

        val scaled =
            Bitmap.createScaledBitmap(
                bitmap,
                size,
                size,
                true
            )

        val left =
            (qrSize - size) / 2f

        val top =
            (qrSize - size) / 2f

        canvas.drawBitmap(
            scaled,
            left,
            top,
            null
        )
    }
}