package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypetapplication.features.english.data.EnglishAllIrregularScreenContent
import com.example.mypetapplication.utils.log
import com.example.presentationmodule.compose.EnglishIrregularVerbRowCell

@Composable
fun EnglishAllIrregularVerbsScreen(
    contentState: State<EnglishAllIrregularScreenContent?>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        log("Column")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.secondary)
        ) {
            log("Row")
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp),
                text = "Infinitive",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp),
                text = "Past Simple", textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 12.dp),
                text = "Past Participle", textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 16.sp
            )
        }
        val items = contentState.value?.items ?: emptyList()
        val checked = contentState.value?.isShowTranslate?: false
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            log("LazyColumn")
            items(items) { item ->
                EnglishIrregularVerbRowCell(item = item, checked)
            }
        }
    }
}