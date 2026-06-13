package com.samayteck.core.util

import com.google.zxing.common.BitMatrix

object MatrixUtils {

    fun moduleSize(
        matrix: BitMatrix,
        size: Int
    ): Float {

        return size.toFloat() /
                matrix.width
    }
}