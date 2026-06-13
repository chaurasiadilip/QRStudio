package com.samayteck.svg

import com.caverock.androidsvg.SVG

object SvgParser {

    fun fromString(
        svgContent: String
    ): Result<SVG> {

        return runCatching {

            SVG.getFromString(
                svgContent
            )
        }
    }

    fun fromAsset(
        assetContent: String
    ): Result<SVG> {

        return runCatching {

            SVG.getFromString(
                assetContent
            )
        }
    }
}