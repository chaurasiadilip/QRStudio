package com.samayteck.core.renderer.background

import android.graphics.Canvas

interface BackgroundRenderer {
    fun draw(
        canvas: Canvas,
        size: Int
    )
}