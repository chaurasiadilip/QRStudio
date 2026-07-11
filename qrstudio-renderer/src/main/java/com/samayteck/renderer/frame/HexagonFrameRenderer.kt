package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer
import kotlin.math.cos
import kotlin.math.sin

internal object HexagonFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }
        
        val labelHeight = if (options.label != null) options.labelSize * 1.2f else 0f
        val path = Path()
        val cx = size / 2f
        val cy = size / 2f - (labelHeight / 2f)
        val radius = size / 2f - options.padding - (labelHeight / 2f)
        
        for (i in 0 until 6) {
            val angle = Math.toRadians((60 * i - 30).toDouble())
            val px = cx + radius * cos(angle).toFloat()
            val py = cy + radius * sin(angle).toFloat()
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
        }
        path.close()
        
        canvas.drawPath(path, paint)
        
        drawLabel(canvas, size, options)
    }
}

private fun drawLabel(canvas: Canvas, size: Int, options: FrameOptions) {
    options.label?.let { text ->
        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = options.labelColor
            textSize = options.labelSize
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD)
        }
        canvas.drawText(text, size / 2f, size - options.padding, textPaint)
    }
}
