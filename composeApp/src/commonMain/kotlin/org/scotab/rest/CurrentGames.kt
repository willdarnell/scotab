package org.scotab.rest

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.*
import kotlinx.datetime.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonArray

class CurrentGames {

    private val client = HttpClient(CIO)

    suspend fun fetchGames(day: String): String {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val targetDate = when (day) {
            "today" -> currentDate
            "tomorrow" -> currentDate.plus(DatePeriod(days = 1))
            "yesterday" -> currentDate.minus(DatePeriod(days = 1))
            else -> throw IllegalArgumentException("Invalid day parameter")
        }.toString()

        val response: HttpResponse = client.get("https://api-nba-v1.p.rapidapi.com/games?date=$targetDate") {
            headers {
                append("x-rapidapi-key", "9661b72e89mshff56832dcc17aacp12e8aejsn6b2fb754e77e")
                append("x-rapidapi-host", "api-nba-v1.p.rapidapi.com")
            }
        }
        val responseBody = response.bodyAsText()
        val jsonElement = Json.parseToJsonElement(responseBody)
        val gamesArray = jsonElement.jsonObject["response"]?.jsonArray
        println(gamesArray)
        return gamesArray.toString()
    }

    fun close() {
        client.close()
    }
}