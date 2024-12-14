// File: composeApp/src/commonMain/kotlin/org/scotab/components/GameList.kt
package org.scotab.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.scotab.models.Game

@Composable
fun GameList(games: List<Game>) {
    var showGames by remember { mutableStateOf(false) }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { showGames = !showGames }) {
            Text(if (showGames) "Hide Games" else "Show Games")
        }
        AnimatedVisibility(showGames) {
            LazyColumn {
                items(games) { game ->
                    GameItem(game)
                }
            }
        }
    }
}