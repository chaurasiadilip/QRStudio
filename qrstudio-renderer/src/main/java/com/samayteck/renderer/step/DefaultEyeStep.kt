package com.samayteck.renderer.step

import com.samayteck.core.renderer.RenderContext
import com.samayteck.core.renderer.step.EyeStep
import com.samayteck.renderer.factory.EyeRendererFactory
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

        val renderer =
            EyeRendererFactory.create(
                context.options.eyeShape
            )

        renderer.draw(
            context.canvas,
            offsetX,
            offsetY,
            eyeSize,
            paint,
            context.options.backgroundColor
        )

        renderer.draw(
            context.canvas,
            offsetX + (matrix.width - 7) *
                    moduleSize,
            offsetY,
            eyeSize,
            paint,
            context.options.backgroundColor
        )

        renderer.draw(
            context.canvas,
            offsetX,
            offsetY + (matrix.width - 7) *
                    moduleSize,
            eyeSize,
            paint,
            context.options.backgroundColor
        )
    }
}