import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.jetbrains.skia.Image
import org.scotab.models.GameStatistics
import org.scotab.models.stubbedGameStats
import org.scotab.rest.GameById
import org.scotab.util.Sanitize

@Composable
fun GameDetailsScreen(gameId: Int, onBack: () -> Unit) {
    var gameStats by remember { mutableStateOf<List<GameStatistics>?>(null) }
    val scope = rememberCoroutineScope()
    val useStubbedData = remember { mutableStateOf(true) }
    val client = HttpClient(CIO)

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
                            Text("Name: ${stat.team["name"]}", fontSize = 18.sp)
                            val imageUrl = stat.team["logo"]
                            var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

                            LaunchedEffect(imageUrl) {
                                scope.launch {
                                    val response: HttpResponse = client.get(imageUrl!!)
                                    val byteArray = response.readBytes()
                                    val bitmap = Image.makeFromEncoded(byteArray).asImageBitmap()
                                    imageBitmap = bitmap
                                }
                            }

                            imageBitmap?.let {
                                Image(
                                    bitmap = it,
                                    contentDescription = "Team Logo",
                                    modifier = Modifier
                                        .height(100.dp)
                                        .fillMaxWidth()
                                )
                            }

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