package com.samayteck.core.renderer.logo

import android.graphics.Canvas

interface LogoRenderer {
    fun draw(
        canvas: Canvas,
        qrSize: Int,
        centerX: Float,
        centerY: Float
    )
}