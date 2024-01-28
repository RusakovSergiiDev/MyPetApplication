package com.example.mypetapplication.features.english.compose

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mypetapplication.features.english.data.EnglishIrregularVerbsState

@Composable
fun EnglishIrregularVerbsCampaignScreen(
    content: EnglishIrregularVerbsState.Campaign
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val campaign = content.campaign
        val missions = campaign.missions
        val currentMissionIndex = campaign.getCurrentMissionIndexState().value
        val currentMission = missions[currentMissionIndex]
        val onMissionCheckCallback = campaign.onMissionCheckCallback

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
        ) {
            for (index in 1 until missions.size) {
                Box(
                    modifier = Modifier
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
            targetState = currentMission, transitionSpec = {
                slideInHorizontally(animationSpec = tween(500),
                    initialOffsetX = { fullWidth -> fullWidth }) togetherWith slideOutHorizontally(animationSpec = tween(500),
                    targetOffsetX = { fullWidth -> -fullWidth })
            }, label = ""
        ) { item ->
            EnglishIrregularVerbMissionCell(
                item,
                onMissionCheckCallback,
            )
        }
    }
}