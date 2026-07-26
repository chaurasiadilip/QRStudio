package com.samayteck.renderer.background

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.samayteck.core.renderer.background.BackgroundRenderer

class ImageBackgroundRenderer(
    private val backgroundImage: Bitmap
) : BackgroundRenderer {

    override fun draw(canvas: Canvas, size: Int) {
        val srcRect = Rect(0, 0, backgroundImage.width, backgroundImage.height)
        val destRect = Rect(0, 0, size, size)
        canvas.drawBitmap(backgroundImage, srcRect, destRect, Paint(Paint.ANTI_ALIAS_FLAG))
    }
}
