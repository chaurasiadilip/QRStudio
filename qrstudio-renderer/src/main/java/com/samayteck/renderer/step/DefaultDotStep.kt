package com.samayteck.renderer.step

import com.samayteck.core.renderer.RenderContext
import com.samayteck.core.renderer.step.DotStep
import com.samayteck.renderer.factory.DefaultDotRendererFactory
import com.samayteck.renderer.gradient.GradientPaintFactory

internal class DefaultDotStep :
    DotStep {

    override fun render(
        context: RenderContext,
    ) {

        val matrix =
            context.matrix

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
            DefaultDotRendererFactory
                .create(
                    context.options.dotShape
                )

        val moduleSize =
            context.moduleSize

        val offsetX =
            context.offsetX

        val offsetY =
            context.offsetY

        for (x in 0 until matrix.width) {

            for (y in 0 until matrix.height) {

                if (!matrix[x, y]) {
                    continue
                }

                if (
                    isEyeArea(
                        x,
                        y,
                        matrix.width
                    )
                ) {
                    continue
                }

                renderer.draw(
                    canvas =
                        context.canvas,

                    x = offsetX + (x * moduleSize),

                    y = offsetY + (y * moduleSize),

                    moduleSize =
                        moduleSize,

                    paint = paint
                )
            }
        }
    }

    private fun isEyeArea(
        x: Int,
        y: Int,
        size: Int
    ): Boolean {

        // Eyes are 7x7. We skip 8x8 to include the 1-module separator.
        return (
            (x < 8 && y < 8) ||
            (x >= size - 8 && y < 8) ||
            (x < 8 && y >= size - 8)
        )
    }
}