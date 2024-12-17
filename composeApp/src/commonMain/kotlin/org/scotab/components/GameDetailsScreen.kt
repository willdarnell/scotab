package org.scotab.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.scotab.models.GameByIdResponse
import org.scotab.rest.GameById

@Composable
fun GameDetailsScreen(gameId: Int, onBack: () -> Unit) {
    var gameStats by remember { mutableStateOf<List<GameByIdResponse>?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(gameId) {
        scope.launch {
            val gameById = GameById()
            gameStats = gameById.getGameById(gameId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onBack) {
            Text("Back")
        }

        Spacer(modifier = Modifier.height(16.dp))

        gameStats?.let { stats ->
            stats.forEach { stat ->
                Text("Team ID: ${stat.team["id"]}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Field Goals: ${stat.field_goals["total"]}/${stat.field_goals["attempts"]} (${stat.field_goals["percentage"]}%)")
                Text("Three Point Goals: ${stat.threepoint_goals["total"]}/${stat.threepoint_goals["attempts"]} (${stat.threepoint_goals["percentage"]}%)")
                Text("Free Throws: ${stat.freethrows_goals["total"]}/${stat.freethrows_goals["attempts"]} (${stat.freethrows_goals["percentage"]}%)")
                Text("Rebounds: ${stat.rebounds["total"]} (Offense: ${stat.rebounds["offence"]}, Defense: ${stat.rebounds["defense"]})")
                Text("Assists: ${stat.assists}")
                Text("Steals: ${stat.steals}")
                Text("Blocks: ${stat.blocks}")
                Text("Turnovers: ${stat.turnovers}")
                Text("Personal Fouls: ${stat.personal_fouls}")
                Spacer(modifier = Modifier.height(16.dp))
            }
        } ?: run {
            CircularProgressIndicator()
        }
    }
}