package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypetapplication.features.english.data.EnglishAllIrregularVerbsScreenContent
import com.example.mypetapplication.utils.log
import com.example.presentationmodule.compose.EnglishIrregularVerbRowCell

@Composable
fun EnglishAllIrregularVerbsScreen(
    contentState: State<EnglishAllIrregularVerbsScreenContent?>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
        val items = remember { contentState.value?.items ?: emptyList() }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(
                count = items.size,
                key = {
                    items[it].infinitive
                },
                itemContent = { index ->
                    EnglishIrregularVerbRowCell(item = items[index])
                }
            )
        }
    }
}