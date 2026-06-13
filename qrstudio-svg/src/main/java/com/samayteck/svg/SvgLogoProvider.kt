package com.samayteck.svg

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.RawRes

object SvgLogoProvider {

    fun fromSvgString(
        svgContent: String,
        size: Int
    ): Result<Bitmap> {

        return SvgParser
            .fromString(svgContent)
            .fold(
                onSuccess = { svg ->

                    SvgBitmapConverter.convert(
                        svg = svg,
                        width = size,
                        height = size
                    )
                },
                onFailure = {
                    Result.failure(it)
                }
            )
    }

    fun fromRawResource(
        context: Context,
        @RawRes resId: Int,
        size: Int
    ): Result<Bitmap> {

        return runCatching {

            require(size > 0) {
                "Size must be greater than 0"
            }

            context.resources
                .openRawResource(resId)
                .bufferedReader()
                .use { reader ->

                    reader.readText()
                }
        }.fold(
            onSuccess = { svgContent ->

                fromSvgString(
                    svgContent = svgContent,
                    size = size
                )
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    fun fromAsset(
        context: Context,
        fileName: String,
        size: Int
    ): Result<Bitmap> {

        return runCatching {

            require(fileName.isNotBlank()) {
                "Asset file name cannot be blank"
            }

            require(size > 0) {
                "Size must be greater than 0"
            }

            context.assets
                .open(fileName)
                .bufferedReader()
                .use { reader ->

                    reader.readText()
                }
        }.fold(
            onSuccess = { svgContent ->

                fromSvgString(
                    svgContent = svgContent,
                    size = size
                )
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}