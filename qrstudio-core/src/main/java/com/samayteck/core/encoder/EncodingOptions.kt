package com.samayteck.core.encoder

data class EncodingOptions(
    val margin: Int = 1,

    val errorCorrectionLevel:
    ErrorCorrectionLevel =
        ErrorCorrectionLevel.HIGH
)
