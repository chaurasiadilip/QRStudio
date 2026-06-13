package com.samayteck.qrstudiotest.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.samayteck.core.model.FrameStyle

@Composable
fun FrameSelector(
    selected: FrameStyle,
    onSelected: (FrameStyle) -> Unit
) {

    Row {

        FrameStyle.entries.forEach {

            FilterChip(
                selected =
                    selected == it,

                onClick = {
                    onSelected(it)
                },

                label = {
                    Text(it.name)
                }
            )
        }
    }
}