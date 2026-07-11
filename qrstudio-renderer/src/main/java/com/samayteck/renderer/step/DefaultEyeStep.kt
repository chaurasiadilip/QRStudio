package com.samayteck.renderer.step

import com.samayteck.core.renderer.RenderContext
import com.samayteck.core.renderer.step.EyeStep
import com.samayteck.renderer.factory.EyeBallRendererFactory
import com.samayteck.renderer.factory.EyeFrameRendererFactory
import com.samayteck.renderer.gradient.GradientPaintFactory

internal class DefaultEyeStep :
    EyeStep {

    override fun render(
        context: RenderContext
    ) {

        val matrix =
            context.matrix

        val moduleSize =
            context.moduleSize

        val offsetX =
            context.offsetX

        val offsetY =
            context.offsetY

        val eyeSize =
            moduleSize * 7

        val paint =
            GradientPaintFactory.create(
                context.options.size,
                context.options.gradientStyle
            ).apply {
                if (shader == null) {
                    color = android.graphics.Color.BLACK
                }
            }

        val frameRenderer =
            EyeFrameRendererFactory.create(
                context.options.eyeFrameShape
            )

        val ballRenderer =
            EyeBallRendererFactory.create(
                context.options.eyeBallShape
            )

        val clearPaint = android.graphics.Paint(
            android.graphics.Paint.ANTI_ALIAS_FLAG
        ).apply {
            color = context.options.backgroundColor
        }

        val framePaint = context.options.eyeFrameColor?.let {
            android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
                color = it
                style = android.graphics.Paint.Style.FILL
            }
        } ?: paint

        val ballPaint = context.options.eyeBallColor?.let {
            android.graphics.Paint(android.graphics.Paint.ANTI_ALIAS_FLAG).apply {
                color = it
                style = android.graphics.Paint.Style.FILL
            }
        } ?: paint

        drawEye(
            context.canvas,
            offsetX,
            offsetY,
            eyeSize,
            framePaint,
            ballPaint,
            clearPaint,
            frameRenderer,
            ballRenderer,
            context.options.backgroundColor
        )

        drawEye(
            context.canvas,
            offsetX + (matrix.width - 7) * moduleSize,
            offsetY,
            eyeSize,
            framePaint,
            ballPaint,
            clearPaint,
            frameRenderer,
            ballRenderer,
            context.options.backgroundColor
        )

        drawEye(
            context.canvas,
            offsetX,
            offsetY + (matrix.width - 7) * moduleSize,
            eyeSize,
            framePaint,
            ballPaint,
            clearPaint,
            frameRenderer,
            ballRenderer,
            context.options.backgroundColor
        )
    }

    private fun drawEye(
        canvas: android.graphics.Canvas,
        left: Float,
        top: Float,
        size: Float,
        framePaint: android.graphics.Paint,
        ballPaint: android.graphics.Paint,
        clearPaint: android.graphics.Paint,
        frameRenderer: com.samayteck.core.renderer.eye.EyeFrameRenderer,
        ballRenderer: com.samayteck.core.renderer.eye.EyeBallRenderer,
        backgroundColor: Int
    ) {
        val cx = left + size / 2f
        val cy = top + size / 2f

        // 1. Draw Frame (7x7 area) - now handles its own inner clearing to match shape
        frameRenderer.draw(canvas, left, top, size, framePaint, backgroundColor)

        // 2. Draw Ball (3x3 area)
        val ballSize = size * (3f / 7f)
        ballRenderer.draw(canvas, cx, cy, ballSize, ballPaint)
    }
}