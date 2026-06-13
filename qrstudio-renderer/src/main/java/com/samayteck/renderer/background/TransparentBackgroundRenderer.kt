package com.samayteck.renderer.background

import android.graphics.Canvas
import com.samayteck.core.renderer.background.BackgroundRenderer

object TransparentBackgroundRenderer :
    BackgroundRenderer {

    override fun draw(
        canvas: Canvas,
        size: Int
    ) = Unit
}