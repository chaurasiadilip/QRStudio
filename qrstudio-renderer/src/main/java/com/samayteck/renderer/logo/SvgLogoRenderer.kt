package com.samayteck.renderer.logo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import com.samayteck.core.renderer.logo.LogoRenderer

class SvgLogoRenderer(
    private val bitmap: Bitmap,
    private val logoPercent: Float
) : LogoRenderer {

    override fun draw(
        canvas: Canvas,
        qrSize: Int,
        centerX: Float,
        centerY: Float
    ) {
        if (logoPercent <= 0f) return

        val maxLogoSize = (qrSize * logoPercent).toInt()
        val ratio = bitmap.width.toFloat() / bitmap.height.toFloat()

        val targetWidth: Int
        val targetHeight: Int

        if (ratio > 1f) {
            targetWidth = maxLogoSize
            targetHeight = (maxLogoSize / ratio).toInt()
        } else {
            targetHeight = maxLogoSize
            targetWidth = (maxLogoSize * ratio).toInt()
        }

        val matrix = Matrix()
        val scale = targetWidth.toFloat() / bitmap.width
        
        // Scale and center the bitmap using a Matrix
        matrix.postScale(scale, scale)
        matrix.postTranslate(
            centerX - (targetWidth / 2f),
            centerY - (targetHeight / 2f)
        )

        canvas.drawBitmap(
            bitmap,
            matrix,
            null
        )
    }
}
