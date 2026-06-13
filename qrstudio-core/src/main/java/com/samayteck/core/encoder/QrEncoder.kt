package com.samayteck.core.encoder

interface QrEncoder {
    fun encode(
        content: String,
        size: Int,
        options: EncodingOptions
    ): EncoderResult
}