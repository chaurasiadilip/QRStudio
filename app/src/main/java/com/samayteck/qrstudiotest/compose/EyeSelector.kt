package com.samayteck.qrstudiotest.compose

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samayteck.core.model.EyeShape

@Composable
fun EyeSelector(
    selected: EyeShape,
    onSelected: (EyeShape) -> Unit
) {

    Row(
        modifier =
            Modifier
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        EyeShape.entries.forEach {

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