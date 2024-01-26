package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.mypetapplication.features.english.data.EnglishRulesScreenContent
import com.example.presentationmodule.compose.EnglishConditionalRowCell
import com.example.presentationmodule.compose.EnglishTimeRuleRowCell

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EnglishRulesScreen(
    contentState: State<EnglishRulesScreenContent?>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val englishRulesModel = contentState.value?.englishRulesModel
        val timeRules = englishRulesModel?.timeRules ?: emptyList()
        val conditionals = englishRulesModel?.conditionals ?: emptyList()

        val pagerState = rememberPagerState(pageCount = { 2 })

        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(timeRules) { item ->
                        EnglishTimeRuleRowCell(item = item)
                    }
                }

                1 -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    items(conditionals) { item ->
                        EnglishConditionalRowCell(item = item)
                    }
                }
            }
        }
    }
}