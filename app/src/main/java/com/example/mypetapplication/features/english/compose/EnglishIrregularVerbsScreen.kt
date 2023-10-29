package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentationmodule.AppTheme
import com.example.presentationmodule.R
import com.example.presentationmodule.compose.topappbar.BackTopAppBarWithAction

@Composable
fun EnglishIrregularVerbsScreen(
    onBackClicked: () -> Unit,
    onSeeAllClicked: () -> Unit
) {
    AppTheme {
        BackTopAppBarWithAction(
            titleResId = R.string.label_englishIrregularVerbs,
            onBackClicked = { onBackClicked.invoke() },
            actions = {
                Text(
                    modifier = Modifier
                        .clickable { onSeeAllClicked.invoke() }
                        .padding(8.dp),
                    text = stringResource(id = R.string.label_label_seeAll),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                )
            }
        ) {

        }
    }
}