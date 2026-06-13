package com.samayteck.core.api

sealed class StyledQrException(
    message: String
) : Exception(message) {

    data object EmptyContent :
        StyledQrException(
            "QR content cannot be empty."
        )

    data object InvalidSize :
        StyledQrException(
            "QR size must be greater than zero."
        )

    data object InvalidLogoSize :
        StyledQrException(
            "Logo size must be between 0f and 0.25f."
        )
}