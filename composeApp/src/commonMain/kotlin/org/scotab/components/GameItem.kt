package org.scotab.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.scotab.models.Game

@Composable
fun GameItem(game: Game) {
    Column(Modifier.padding(8.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                Text(text = game.teams.home.name)
            }
            Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                Text(text = game.teams.away.name)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Final Score: ${game.scores.home.total} - ${game.scores.away.total}")
    }
}