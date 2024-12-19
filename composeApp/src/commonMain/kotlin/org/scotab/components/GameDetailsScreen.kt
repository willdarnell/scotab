import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import org.scotab.models.stubbedGameStats
import org.scotab.rest.GameById
import org.scotab.util.Sanitize

@Composable
fun GameDetailsScreen(gameId: Int, onBack: () -> Unit) {
    var gameStats by remember { mutableStateOf<List<GameStatistics>?>(null) }
    val scope = rememberCoroutineScope()
    val useStubbedData = remember { mutableStateOf(true) }

    LaunchedEffect(gameId) {
        scope.launch {
            if (useStubbedData.value) {
                val sanitizedJson = stubbedGameStats
                val json = Json {
                    serializersModule = SerializersModule {
                        contextual(CustomGameStatisticsSerializer)
                    }
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                }
                gameStats = json.decodeFromString(sanitizedJson)
            } else {
                val gameById = GameById()
                val gameStatsString = gameById.getGameById(gameId)
                val sanitizedJson = Sanitize.sanitizeResponse(gameStatsString)
                println(sanitizedJson)
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
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(stats) { stat ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text("Team ID: ${stat.team["id"]}", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text("Name: ${stat.team["name"]}", fontSize = 18.sp)
                            Text("Nickname: ${stat.team["nickname"]}", fontSize = 18.sp)
                            Text("Code: ${stat.team["code"]}", fontSize = 18.sp)
                            Text("Logo: ${stat.team["logo"]}", fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            stat.statistics.forEach { (key, value) ->
                                Text("$key: ${value ?: "N/A"}", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        } ?: run {
            CircularProgressIndicator()
        }
    }
}