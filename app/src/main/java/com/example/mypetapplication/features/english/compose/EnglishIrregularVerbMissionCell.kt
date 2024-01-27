package com.example.mypetapplication.features.english.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.datamodule.models.english.EnglishIrregularMissionModel

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