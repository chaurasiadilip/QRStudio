package com.samayteck.qrstudiotest.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.samayteck.core.model.BackgroundPattern

@Composable
fun BackgroundSelector(
    selected: BackgroundPattern,
    onSelected: (BackgroundPattern) -> Unit
) {

    Row {

        BackgroundPattern.entries.forEach {

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