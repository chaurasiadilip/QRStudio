package com.samayteck.renderer.frame

import android.graphics.*
import com.samayteck.core.model.FrameOptions
import com.samayteck.core.renderer.frame.FrameRenderer
import kotlin.math.cos
import kotlin.math.sin

internal object BadgeFrameRenderer : FrameRenderer {
    override fun draw(canvas: Canvas, size: Int, options: FrameOptions) {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = options.strokeWidth
            color = options.frameColor
        }
        
        val labelHeight = if (options.label != null) options.labelSize * 1.5f else 0f
        val padding = options.padding
        val radius = (size - padding * 2 - labelHeight / 2f) / 2f
        val cx = size / 2f
        val cy = size / 2f - labelHeight / 2f
        
        // Draw serrated "Badge" edge
        val path = Path()
        val points = 32
        val innerRadius = radius
        val outerRadius = radius + 15f
        
        for (i in 0 until points * 2) {
            val angle = i * Math.PI / points
            val r = if (i % 2 == 0) outerRadius else innerRadius
            val x = cx + (r * cos(angle)).toFloat()
            val y = cy + (r * sin(angle)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()
        canvas.drawPath(path, paint)
        
        // Draw inner circle for a "Seal" look
        canvas.drawCircle(cx, cy, radius - 15f, paint)

        drawLabel(canvas, size, options)
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
