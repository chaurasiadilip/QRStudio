package com.samayteck.renderer.logo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import com.samayteck.core.constants.QrConstants
import com.samayteck.core.renderer.logo.LogoRenderer

class VectorLogoRenderer(
    private val drawable: Drawable,
    private val logoPercent: Float
) : LogoRenderer {

    override fun draw(
        canvas: Canvas,
        qrSize: Int
    ) {

        if (
            logoPercent <= 0f ||
            logoPercent >
            QrConstants.MAX_LOGO_PERCENT
        ) {
            return
        }

        val size =
            (qrSize * logoPercent)
                .toInt()

        val bitmap =
            Bitmap.createBitmap(
                size,
                size,
                Bitmap.Config.ARGB_8888
            )

        val logoCanvas =
            Canvas(bitmap)

        drawable.setBounds(
            0,
            0,
            size,
            size
        )

        drawable.draw(
            logoCanvas
        )

        val left =
            (qrSize - size) / 2f

        val top =
            (qrSize - size) / 2f

        canvas.drawBitmap(
            bitmap,
            left,
            top,
            null
        )
    }
}