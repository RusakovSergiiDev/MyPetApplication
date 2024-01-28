package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.datamodule.models.english.EnglishItVerbModel
import com.example.mypetapplication.features.english.data.EnglishItVerbsScreenContent

@Composable
fun EnglishItVerbsScreen(
    screenContentState: State<EnglishItVerbsScreenContent?>
) {
    val content = screenContentState.value ?: return
    val items = content.items
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) {
            EnglishItVerbItemCell(it)
        }
    }
}

@Composable
fun EnglishItVerbItemCell(item: EnglishItVerbModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .weight(1f), text = item.english
        )
        Text(
            modifier = Modifier
                .weight(1f), text = item.ukrainian
        )
    }
}