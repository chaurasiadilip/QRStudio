package com.samayteck.renderer.logo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import com.samayteck.core.constants.QrConstants
import com.samayteck.core.renderer.logo.LogoRenderer

import com.samayteck.core.model.LogoShape

class BitmapLogoRenderer(
    private val logo: Bitmap,
    private val logoPercent: Float,
    private val drawBackground: Boolean = true,
    private val backgroundColor: Int = android.graphics.Color.WHITE,
    private val backgroundPadding: Float = -1f, // -1 means auto-calculate
    private val logoShape: LogoShape = LogoShape.CIRCLE
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

        // Calculate max area for logo (side length of a square)
        val maxLogoSize = (qrSize * logoPercent).toInt()

        // Trim transparent borders if it's a PNG to avoid "taking a lot of area"
        val trimmedLogo = trimBitmap(logo)
        val trimmedRatio = trimmedLogo.width.toFloat() / trimmedLogo.height.toFloat()

        val targetWidth = maxOf(1, if (trimmedRatio > 1f) {
            maxLogoSize
        } else {
            (maxLogoSize * trimmedRatio).toInt()
        })

        val targetHeight = maxOf(1, if (trimmedRatio > 1f) {
            (maxLogoSize / trimmedRatio).toInt()
        } else {
            maxLogoSize
        })

        if (drawBackground) {
            val bgPadding = if (backgroundPadding >= 0) backgroundPadding else qrSize * 0.015f
            LogoBackgroundRenderer.draw(
                canvas = canvas,
                centerX = centerX,
                centerY = centerY,
                width = targetWidth + bgPadding * 2f,
                height = targetHeight + bgPadding * 2f,
                shape = logoShape,
                backgroundColor = backgroundColor
            )
        }

        val matrix = Matrix()
        val scaleX = targetWidth.toFloat() / trimmedLogo.width
        val scaleY = targetHeight.toFloat() / trimmedLogo.height

        // Scale the logo to the target dimensions
        matrix.postScale(scaleX, scaleY)
        
        // Translate it to be centered at (centerX, centerY)
        matrix.postTranslate(
            centerX - (targetWidth / 2f),
            centerY - (targetHeight / 2f)
        )

        canvas.drawBitmap(
            trimmedLogo,
            matrix,
            null
        )
    }

    private fun trimBitmap(source: Bitmap): Bitmap {
        var firstX = 0
        var firstY = 0
        var lastX = source.width - 1
        var lastY = source.height - 1

        val pixels = IntArray(source.width * source.height)
        source.getPixels(pixels, 0, source.width, 0, 0, source.width, source.height)

        // Find first X
        loop@ for (x in 0 until source.width) {
            for (y in 0 until source.height) {
                if (android.graphics.Color.alpha(pixels[y * source.width + x]) > 10) {
                    firstX = x
                    break@loop
                }
            }
        }

        // Find first Y
        loop@ for (y in 0 until source.height) {
            for (x in 0 until source.width) {
                if (android.graphics.Color.alpha(pixels[y * source.width + x]) > 10) {
                    firstY = y
                    break@loop
                }
            }
        }

        // Find last X
        loop@ for (x in source.width - 1 downTo firstX) {
            for (y in 0 until source.height) {
                if (android.graphics.Color.alpha(pixels[y * source.width + x]) > 10) {
                    lastX = x
                    break@loop
                }
            }
        }

        // Find last Y
        loop@ for (y in source.height - 1 downTo firstY) {
            for (x in 0 until source.width) {
                if (android.graphics.Color.alpha(pixels[y * source.width + x]) > 10) {
                    lastY = y
                    break@loop
                }
            }
        }

        val trimmedWidth = lastX - firstX + 1
        val trimmedHeight = lastY - firstY + 1

        return if (trimmedWidth <= 0 || trimmedHeight <= 0) {
            source
        } else if (firstX == 0 && firstY == 0 && trimmedWidth == source.width && trimmedHeight == source.height) {
            source
        } else {
            Bitmap.createBitmap(source, firstX, firstY, trimmedWidth, trimmedHeight)
        }
    }
}
