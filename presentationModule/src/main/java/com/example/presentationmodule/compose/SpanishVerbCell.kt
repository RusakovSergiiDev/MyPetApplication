package com.example.presentationmodule.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentationmodule.data.SpanishVerbUiItem

@Composable
fun SpanishVerbRowCell(
    item: SpanishVerbUiItem,
    index: Int
) {
    val isHighlighted = index % 2 != 0
    val cellColor = if (isHighlighted) MaterialTheme.colorScheme.secondaryContainer
    else MaterialTheme.colorScheme.background
    val textColor = if (isHighlighted) MaterialTheme.colorScheme.onSecondaryContainer
    else MaterialTheme.colorScheme.onBackground
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(cellColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 12.dp, bottom = 12.dp),
                text = item.spanish,
                textAlign = TextAlign.Center,
                color = textColor,
                fontSize = 14.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = 12.dp, bottom = 12.dp),
                text = item.ukrainian,
                textAlign = TextAlign.Center,
                color = textColor,
                fontSize = 14.sp,
            )
        }
    }
}