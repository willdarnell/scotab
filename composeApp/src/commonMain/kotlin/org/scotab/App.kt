// File: composeApp/src/commonMain/kotlin/org/scotab/App.kt
package org.scotab

import CustomGameSerializer
import GameList
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.websocket.Frame
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scotab.models.Game
import org.scotab.rest.CurrentGames
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.scotab.components.HomeScreen
import org.scotab.util.Sanitize

@Composable
@Preview
fun App() {
//    MaterialTheme {
//        var games by remember { mutableStateOf<List<Game>>(emptyList()) }
//        val scope = rememberCoroutineScope()
//
//        LaunchedEffect(Unit) {
//            scope.launch {
//                val currentGames = CurrentGames()
//                val gamesJson = currentGames.fetchGames()
//                val sanitizedJson = Sanitize.sanitizeResponse(gamesJson)
//                val json = Json { serializersModule = SerializersModule {
//                    contextual(CustomGameSerializer)
//                }
//                    ignoreUnknownKeys = true
//                    coerceInputValues = true
//                }
//                try {
//                    games = json.decodeFromString(sanitizedJson)
//                } catch (e: Exception) {
//                    println(e.printStackTrace())
//                }
//                currentGames.close()
//            }
//        }
//
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//             GameList(games)
//            //Frame.Text("Hello, World!")
//        }
//    }
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            HomeScreen()
        }
    }
}