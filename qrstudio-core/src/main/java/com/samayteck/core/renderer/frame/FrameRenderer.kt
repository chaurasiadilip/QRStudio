package com.samayteck.core.renderer.frame

import android.graphics.Canvas
import com.samayteck.core.model.FrameOptions

interface FrameRenderer {
    fun draw(
        canvas: Canvas,
        size: Int,
        options: FrameOptions
    )
}