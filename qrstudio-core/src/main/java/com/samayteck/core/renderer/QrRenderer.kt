package com.samayteck.core.renderer

import com.google.zxing.common.BitMatrix
import com.samayteck.core.model.StyledQrOptions

interface QrRenderer {
    fun render(
        matrix: BitMatrix,
        options: StyledQrOptions
    ): RenderResult
}