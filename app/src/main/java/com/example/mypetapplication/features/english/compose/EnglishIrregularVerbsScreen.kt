package com.example.mypetapplication.features.english.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.datamodule.models.english.EnglishIrregularMissionModel
import com.example.mypetapplication.features.english.data.EnglishIrregularVerbsScreenContent
import com.example.mypetapplication.utils.log
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EnglishIrregularVerbsScreen(
    contentState: State<EnglishIrregularVerbsScreenContent?>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        val content = contentState.value ?: return@Column
        val campaign = content.campaign
        val missions = campaign.missions
        val currentMissionIndex = content.currentMissionIndex.value
        val currentMission = missions[currentMissionIndex]
        val currentMissionCheckCallback = content.onMissionCheckCallback

        val pagerState = rememberPagerState(pageCount = { missions.size })
        val coroutineScope = rememberCoroutineScope()

        DisposableEffect(currentMissionIndex) {
            coroutineScope.launch {
                log("Counter changed: $currentMissionIndex")
                pagerState.animateScrollToPage(currentMissionIndex)
            }
            onDispose {}
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        ) {
            for (index in 1 until missions.size) {
                Box(
                    modifier =
                    Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .background(
                            color = if (index <= currentMissionIndex) {
                                MaterialTheme.colorScheme.secondary
                            } else {
                                Color.Transparent
                            }
                        )
                )
            }
        }

        AnimatedContent(
            targetState = currentMission,
            transitionSpec = {
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { fullWidth -> fullWidth }
                ) togetherWith
                        slideOutHorizontally(
                            animationSpec = tween(500),
                            targetOffsetX = { fullWidth -> -fullWidth }
                        )
            }, label = ""
        ) { item ->
            EnglishIrregularVerbMissionCell(
                item,
                currentMissionCheckCallback,
            )
        }
    }
}

@Composable
fun EnglishIrregularVerbMissionCell(
    mission: EnglishIrregularMissionModel,
    missionCheckCallback: (EnglishIrregularMissionModel) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(text = mission.getInfinitive())
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Button(
                modifier = Modifier.align(alignment = Alignment.Center),
                onClick = {
                    missionCheckCallback.invoke(mission)
                }
            ) {
                Text(text = "Check")
            }
        }
    }
}