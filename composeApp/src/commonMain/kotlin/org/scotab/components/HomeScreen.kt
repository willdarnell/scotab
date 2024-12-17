package org.scotab.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
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
import org.scotab.models.Game
import org.scotab.rest.CurrentGames
import org.scotab.util.Sanitize
import CustomGameSerializer
import GameList

@Composable
fun HomeScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Today", "Yesterday", "Tomorrow")
    var games by remember { mutableStateOf<List<Game>>(emptyList()) }
    var selectedGameId by remember { mutableStateOf<Int?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            val currentGames = CurrentGames()
            val gamesJson = currentGames.fetchGames("today")
            val sanitizedJson = Sanitize.sanitizeResponse(gamesJson)
            val json = Json {
                serializersModule = SerializersModule {
                    contextual(CustomGameSerializer)
                }
                ignoreUnknownKeys = true
                coerceInputValues = true
            }
            try {
                games = json.decodeFromString(sanitizedJson)
            } catch (e: Exception) {
                println(e.printStackTrace())
            }
            currentGames.close()
        }
    }

    if (selectedGameId == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App name
            Text(
                text = "ScoTab",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Horizontal navigation bar
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            val day = when (index) {
                                0 -> "today"
                                1 -> "yesterday"
                                2 -> "tomorrow"
                                else -> "today"
                            }
                            scope.launch {
                                val currentGames = CurrentGames()
                                val gamesJson = currentGames.fetchGames(day)
                                val sanitizedJson = Sanitize.sanitizeResponse(gamesJson)
                                val json = Json {
                                    serializersModule = SerializersModule {
                                        contextual(CustomGameSerializer)
                                    }
                                    ignoreUnknownKeys = true
                                    coerceInputValues = true
                                }
                                try {
                                    games = json.decodeFromString(sanitizedJson)
                                } catch (e: Exception) {
                                    println(e.printStackTrace())
                                }
                                currentGames.close()
                            }
                        },
                        text = { Text(title) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Content based on selected tab
            when (selectedTab) {
                0 -> GameList(games) { gameId -> selectedGameId = gameId }
                1 -> GameList(games) { gameId -> selectedGameId = gameId }
                2 -> GameList(games) { gameId -> selectedGameId = gameId }
            }
        }
    } else {
        GameDetailsScreen(gameId = selectedGameId!!) {
            selectedGameId = null
        }
    }
}