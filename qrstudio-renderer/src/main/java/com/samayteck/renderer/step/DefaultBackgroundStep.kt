package com.samayteck.renderer.step

import com.samayteck.core.renderer.RenderContext
import com.samayteck.renderer.factory.DefaultBackgroundRendererFactory
import com.samayteck.core.renderer.step.BackgroundStep

internal class DefaultBackgroundStep : BackgroundStep {

    override fun render(
        context: RenderContext
    ) {

        val renderer =
            DefaultBackgroundRendererFactory
                .create(
                    style =
                        context.options.backgroundStyle,

                    pattern =
                        context.options.backgroundPattern,

                    backgroundColor =
                        context.options.backgroundColor
                )

        renderer.draw(
            context.canvas,
            context.options.size
        )
    }
}