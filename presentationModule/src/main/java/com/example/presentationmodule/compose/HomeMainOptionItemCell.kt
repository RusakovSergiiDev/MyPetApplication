package com.example.presentationmodule.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentationmodule.data.HomeMainOptionUiItem

@Composable
fun HomeMainOptionItemCell(item: HomeMainOptionUiItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            .clickable { item.callback?.invoke() }
            .padding(
                paddingValues = PaddingValues(
                    start = 24.dp,
                    top = 16.dp,
                    end = 24.dp,
                    bottom = 24.dp
                )
            )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = item.title,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = 24.sp
        )
        item.description?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = it,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 16.sp
            )
        }
    }
}