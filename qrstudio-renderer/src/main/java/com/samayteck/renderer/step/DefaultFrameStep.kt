package com.samayteck.renderer.step

import com.samayteck.core.renderer.RenderContext
import com.samayteck.core.renderer.step.FrameStep
import com.samayteck.renderer.factory.FrameRendererFactory

internal class DefaultFrameStep :
    FrameStep {

    override fun render(
        context: RenderContext
    ) {

        val renderer =
            FrameRendererFactory.create(
                context.options
                    .frameOptions
                    .frameStyle
            )

        renderer?.draw(
            context.canvas,
            context.options.size,
            context.options.frameOptions
        )
    }
}