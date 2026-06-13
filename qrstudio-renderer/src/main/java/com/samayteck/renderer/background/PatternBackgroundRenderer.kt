package com.samayteck.renderer.background

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import com.samayteck.core.renderer.background.BackgroundRenderer

class PatternBackgroundRenderer(
    patternBitmap: Bitmap
) : BackgroundRenderer {

    private val paint by lazy {

        Paint(Paint.ANTI_ALIAS_FLAG).apply {

            shader = BitmapShader(
                patternBitmap,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
            )
        }
    }

    override fun draw(
        canvas: Canvas,
        size: Int
    ) {

        canvas.drawRect(
            0f,
            0f,
            size.toFloat(),
            size.toFloat(),
            paint
        )
    }
}