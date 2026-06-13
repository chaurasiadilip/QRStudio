package com.samayteck.core.renderer

import android.graphics.Bitmap
import android.graphics.Canvas
import com.google.zxing.common.BitMatrix
import com.samayteck.core.model.StyledQrOptions

data class RenderContext(
    val matrix: BitMatrix,

    val options: StyledQrOptions,

    val bitmap: Bitmap,

    val canvas: Canvas,

    val moduleSize: Float,

    val offsetX: Float,

    val offsetY: Float
)
