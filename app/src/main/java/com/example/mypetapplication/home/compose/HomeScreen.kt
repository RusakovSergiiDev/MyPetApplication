package com.example.mypetapplication.home.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentationmodule.R
import com.example.presentationmodule.compose.HomeMainOptionItemCell
import com.example.presentationmodule.compose.topappbar.RegularTopAppBar
import com.example.presentationmodule.data.HomeMainOptionUIiItem

@Composable
fun HomeScreen(
    homeMainOptionUiItemsState: State<List<HomeMainOptionUIiItem>>,
    onLogOutClicked: () -> Unit
) {
    RegularTopAppBar(
        titleResId = R.string.label_home,
        actions = {
            Text(
                modifier = Modifier
                    .clickable { onLogOutClicked.invoke() }
                    .padding(8.dp),
                text = stringResource(id = R.string.label_logOut),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 16.sp
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            val items = homeMainOptionUiItemsState.value
            if (items.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            homeMainOptionUiItemsState.value.forEach { item ->
                HomeMainOptionItemCell(item)
            }
            if (items.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}