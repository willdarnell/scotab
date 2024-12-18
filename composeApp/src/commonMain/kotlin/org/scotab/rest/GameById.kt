package org.scotab.rest

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonArray

class GameById {

    private val client = HttpClient(CIO)

    suspend fun getGameById(id: Int): String {
        val gameId = id

        val response: HttpResponse = client.get("https://api-nba-v1.p.rapidapi.com/games/statistics?id=$gameId") {
            headers {
                append("x-rapidapi-key", "9661b72e89mshff56832dcc17aacp12e8aejsn6b2fb754e77e")
                append("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
            }
        }
        val responseBody = response.bodyAsText()
        val jsonElement = Json.parseToJsonElement(responseBody)
        val gamesArray = jsonElement.jsonObject["response"]?.jsonArray
        return gamesArray.toString()
    }

    fun close() {
        client.close()
    }
}