package com.samayteck.renderer.logo

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.samayteck.core.constants.QrConstants
import com.samayteck.core.renderer.logo.LogoRenderer

class BitmapLogoRenderer(
    private val logo: Bitmap,
    private val logoPercent: Float,
    private val drawBackground: Boolean = true,
    private val backgroundPadding: Float = 12f,
    private val backgroundColor: Int = android.graphics.Color.WHITE
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

        val logoSize =
            (qrSize * logoPercent)
                .toInt()

        val left =
            (qrSize - logoSize) / 2f

        val top =
            (qrSize - logoSize) / 2f

        if (drawBackground) {

            val paint = Paint(
                Paint.ANTI_ALIAS_FLAG
            ).apply {
                color = backgroundColor
            }

            val bgRect = RectF(
                left - backgroundPadding,
                top - backgroundPadding,
                left + logoSize + backgroundPadding,
                top + logoSize + backgroundPadding
            )

            val radius =
                backgroundPadding * 2f

            canvas.drawRoundRect(
                bgRect,
                radius,
                radius,
                paint
            )
        }

        val scaled =
            Bitmap.createScaledBitmap(
                logo,
                logoSize,
                logoSize,
                true
            )

        canvas.drawBitmap(
            scaled,
            left,
            top,
            null
        )
    }
}