package com.samayteck.core.encoder
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel as ZxingLevel

internal object ErrorCorrectionFactory {

    fun create(
        level: ErrorCorrectionLevel
    ): ZxingLevel {

        return when (level) {

            ErrorCorrectionLevel.LOW ->
                ZxingLevel.L

            ErrorCorrectionLevel.MEDIUM ->
                ZxingLevel.M

            ErrorCorrectionLevel.QUARTILE ->
                ZxingLevel.Q

            ErrorCorrectionLevel.HIGH ->
                ZxingLevel.H
        }
    }
}