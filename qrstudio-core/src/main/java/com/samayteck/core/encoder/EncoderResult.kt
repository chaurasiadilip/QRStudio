package com.samayteck.core.encoder

import com.google.zxing.common.BitMatrix

data class EncoderResult(
    val matrix: BitMatrix,

    val width: Int,

    val height: Int
)
