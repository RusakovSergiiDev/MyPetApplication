package com.example.mypetapplication.features.spain.compose

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
import com.example.presentationmodule.AppTheme
import com.example.presentationmodule.R
import com.example.presentationmodule.compose.SpanishVerbRowCell
import com.example.presentationmodule.compose.topappbar.BackTopAppBarWithAction
import com.example.presentationmodule.data.SpanishVerbUiItem

@Composable
fun SpanishTop200VerbsScreen(
    onBackClicked: () -> Unit,
    spanishVerbUiItemsState: State<List<SpanishVerbUiItem>>
) {
    AppTheme {
        BackTopAppBarWithAction(
            titleResId = R.string.label_Top200SpanishVerbs,
            onBackClicked = { onBackClicked.invoke() },
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 12.dp),
                        text = "Spanish",
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 16.sp
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(vertical = 12.dp),
                        text = "Ukrainian", textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontSize = 16.sp
                    )
                }
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(spanishVerbUiItemsState.value) { item ->
                        SpanishVerbRowCell(item = item)
                    }
                }
            }
        }
    }
}