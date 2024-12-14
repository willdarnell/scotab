// File: composeApp/src/commonMain/kotlin/org/scotab/App.kt
package org.scotab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.scotab.components.GameList
import org.scotab.models.Game
import org.scotab.rest.CurrentGames
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Composable
@Preview
fun App() {
    MaterialTheme {
        var games by remember { mutableStateOf<List<Game>>(emptyList()) }
        val scope = rememberCoroutineScope()

        LaunchedEffect(Unit) {
            scope.launch {
                val currentGames = CurrentGames()
                val gamesJson = currentGames.fetchGames()
                games = Json.decodeFromString(gamesJson)
                currentGames.close()
            }
        }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            GameList(games)
        }
    }
}