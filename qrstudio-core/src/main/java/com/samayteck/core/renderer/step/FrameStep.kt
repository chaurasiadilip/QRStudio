package com.samayteck.core.renderer.step

import com.samayteck.core.renderer.RenderContext
import com.samayteck.core.renderer.RenderStep

 interface FrameStep :
    RenderStep {

    override fun render(
        context: RenderContext
    )
}