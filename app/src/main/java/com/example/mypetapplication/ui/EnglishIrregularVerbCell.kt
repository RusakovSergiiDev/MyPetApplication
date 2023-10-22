package com.example.mypetapplication.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypetapplication.ui.data.EnglishIrregularVerbUiItem

@Composable
fun EnglishIrregularVerbRowCell(item: EnglishIrregularVerbUiItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 12.dp),
            text = item.infinitive,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 12.dp),
            text = item.pastSimple,
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 12.dp),
            text = item.pastParticiple,
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }

}