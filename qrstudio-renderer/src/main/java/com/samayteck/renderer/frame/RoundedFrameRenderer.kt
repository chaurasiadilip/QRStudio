package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer

internal object RoundedFrameRenderer :
    FrameRenderer {

    override fun draw(
        canvas: Canvas,
        size: Int,
        options: FrameOptions
    ) {

        val paint = Paint(
            Paint.ANTI_ALIAS_FLAG
        ).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }

        val padding = options.padding

        canvas.drawRoundRect(
            RectF(
                padding,
                padding,
                size - padding,
                size - padding
            ),
            48f,
            48f,
            paint
        )

        options.label?.let { text ->
            val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = options.labelColor
                textSize = size * 0.05f
                textAlign = Paint.Align.CENTER
                typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
            
            val textBounds = Rect()
            textPaint.getTextBounds(text, 0, text.length, textBounds)
            
            val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.WHITE
            }
            
            val textX = size / 2f
            val textY = size - padding
            
            canvas.drawRect(
                textX - textBounds.width() / 2f - 20f,
                textY - textBounds.height() - 10f,
                textX + textBounds.width() / 2f + 20f,
                textY + 10f,
                bgPaint
            )

            canvas.drawText(text, textX, textY, textPaint)
        }
    }
}