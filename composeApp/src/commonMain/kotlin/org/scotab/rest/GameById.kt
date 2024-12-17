package org.scotab.rest

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import org.scotab.models.GameByIdResponse
import org.scotab.serializers.GameByIdResponseSerializer

class GameById {

    suspend fun getGameById(gameId: Int): List<GameByIdResponse> {
        val client = HttpClient(CIO)
        val response: HttpResponse =
            client.get("https://api-basketball.p.rapidapi.com/games/statistics/teams?id=$gameId") {
                headers {
                    append("x-rapidapi-key", "9661b72e89mshff56832dcc17aacp12e8aejsn6b2fb754e77e")
                    append("x-rapidapi-host", "api-basketball.p.rapidapi.com")
                }
            }
        val responseBody = response.bodyAsText()
        println(responseBody)
        val jsonElement = Json.parseToJsonElement(responseBody)
        val gamesArray = jsonElement.jsonObject["response"]?.jsonArray
        val json = Json {
            serializersModule = SerializersModule {
                contextual(GameByIdResponseSerializer)
            }
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val stringFormatted = gamesArray.toString()
        return json.decodeFromString(stringFormatted)
    }
}