package com.example.mypetapplication.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mypetapplication.ui.data.HomeMainOptionUIiItem

@Composable
fun HomeMainOptionItemCell(item: HomeMainOptionUIiItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = item.solidColor)
            .clickable { item.onHomeMainOptionItemClicked?.invoke(item.type) }
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = item.title,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        item.description?.let {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = it
            )
        }
    }

}