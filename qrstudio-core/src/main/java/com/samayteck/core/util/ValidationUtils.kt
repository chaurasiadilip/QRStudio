package com.samayteck.core.util

import com.samayteck.core.api.StyledQrException
import com.samayteck.core.constants.QrConstants

internal object ValidationUtils {

    fun validateContent(
        content: String
    ) {
        if (content.isBlank()) {
            throw StyledQrException.EmptyContent
        }
    }

    fun validateSize(
        size: Int
    ) {
        if (size <= 0) {
            throw StyledQrException.InvalidSize
        }
    }

    fun validateLogoPercent(
        percent: Float
    ) {
        if (
            percent < 0f ||
            percent > QrConstants.MAX_LOGO_PERCENT
        ) {
            throw StyledQrException.InvalidLogoSize
        }
    }
}