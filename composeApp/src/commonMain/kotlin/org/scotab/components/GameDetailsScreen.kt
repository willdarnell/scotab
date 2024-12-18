package org.scotab.components

import CustomGameStatisticsSerializer
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.scotab.models.GameStatistics
import org.scotab.rest.GameById
import org.scotab.util.Sanitize

@Composable
fun GameDetailsScreen(gameId: Int, onBack: () -> Unit) {
    var gameStats by remember { mutableStateOf<List<GameStatistics>?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(gameId) {
        scope.launch {
            val gameById = GameById()
            val gameStatsString = gameById.getGameById(gameId)
            val sanitizedJson = Sanitize.sanitizeResponse(gameStatsString)
            val json = Json {
                serializersModule = SerializersModule {
                    contextual(CustomGameStatisticsSerializer)
                }
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
                gameStats = json.decodeFromString(sanitizedJson)
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
                Text("Team ID: ${stat.id}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("Name: ${stat.name}")
                Text("Nickname: ${stat.nickname}")
                Text("Code: ${stat.code}")
                Text("Logo: ${stat.logo}")
                stat.statistics.forEach { (key, value) ->
                    Text("$key: ${value ?: "N/A"}")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        } ?: run {
            CircularProgressIndicator()
        }
    }
}