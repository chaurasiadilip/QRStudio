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
        qrSize: Int,
        centerX: Float,
        centerY: Float
    ) {

        if (
            logoPercent <= 0f ||
            logoPercent >
            QrConstants.MAX_LOGO_PERCENT
        ) {
            return
        }

        val maxLogoSize = (qrSize * logoPercent).toInt()

        // Maintain aspect ratio for vectors
        val intrinsicWidth = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 100
        val intrinsicHeight = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 100
        val ratio = intrinsicWidth.toFloat() / intrinsicHeight.toFloat()
        
        val targetWidth: Int
        val targetHeight: Int
        
        if (ratio > 1f) {
            targetWidth = maxLogoSize
            targetHeight = (maxLogoSize / ratio).toInt()
        } else {
            targetHeight = maxLogoSize
            targetWidth = (maxLogoSize * ratio).toInt()
        }

        val bitmap =
            Bitmap.createBitmap(
                targetWidth,
                targetHeight,
                Bitmap.Config.ARGB_8888
            )

        val logoCanvas =
            Canvas(bitmap)

        drawable.setBounds(
            0,
            0,
            targetWidth,
            targetHeight
        )

        drawable.draw(
            logoCanvas
        )

        val left = centerX - (targetWidth / 2f)
        val top = centerY - (targetHeight / 2f)

        canvas.drawBitmap(
            bitmap,
            left,
            top,
            null
        )
    }
}