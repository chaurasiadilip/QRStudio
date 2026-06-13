package com.samayteck.core.encoder

import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

class DefaultQrEncoder : QrEncoder {

    private val writer =
        QRCodeWriter()

    override fun encode(
        content: String,
        size: Int,
        options: EncodingOptions
    ): EncoderResult {

        require(content.isNotBlank()) {
            "Content cannot be blank."
        }

        require(size > 0) {
            "Size must be > 0."
        }

        require(options.margin >= 0) {
            "Margin cannot be negative."
        }

        val hints = mapOf(

            EncodeHintType.MARGIN
                    to 0,

            EncodeHintType.ERROR_CORRECTION
                    to ErrorCorrectionFactory.create(
                options.errorCorrectionLevel
            )
        )

        val matrix =
            writer.encode(
                content,
                BarcodeFormat.QR_CODE,
                0,
                0,
                hints
            )

        return EncoderResult(
            matrix = matrix,
            width = matrix.width,
            height = matrix.height
        )
    }
}