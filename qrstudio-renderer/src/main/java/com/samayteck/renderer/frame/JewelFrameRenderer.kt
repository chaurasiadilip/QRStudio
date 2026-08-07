package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer
import kotlin.math.cos
import kotlin.math.sin

internal object JewelFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }
        
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        val cx = size / 2f
        val cy = size / 2f - labelHeight / 2f
        val radius = size / 2f - options.padding - (labelHeight / 2f)
        
        // Outer Hexagon
        val outerPath = getHexagonPath(cx, cy, radius)
        canvas.drawPath(outerPath, paint)
        
        // Inner Hexagon (Jewel Cut)
        val innerPath = getHexagonPath(cx, cy, radius - 20f)
        paint.strokeWidth = options.strokeWidth / 2f
        canvas.drawPath(innerPath, paint)
        
        // Accent corners
        for (i in 0 until 6) {
            val angle = Math.toRadians((60 * i - 30).toDouble())
            val x1 = cx + (radius - 5f) * cos(angle).toFloat()
            val y1 = cy + (radius - 5f) * sin(angle).toFloat()
            val x2 = cx + (radius + 15f) * cos(angle).toFloat()
            val y2 = cy + (radius + 15f) * sin(angle).toFloat()
            canvas.drawLine(x1, y1, x2, y2, paint)
        }

        drawLabel(canvas, size, options)
    }

    private fun getHexagonPath(cx: Float, cy: Float, radius: Float): Path {
        val path = Path()
        for (i in 0 until 6) {
            val angle = Math.toRadians((60 * i - 30).toDouble())
            val px = cx + radius * cos(angle).toFloat()
            val py = cy + radius * sin(angle).toFloat()
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
        }
        path.close()
        return path
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
}
