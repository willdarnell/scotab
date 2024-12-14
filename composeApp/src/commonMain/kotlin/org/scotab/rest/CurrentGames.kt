package org.scotab.rest

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.*
import kotlinx.datetime.*

class CurrentGames {

    private val client = HttpClient(CIO)

    suspend fun fetchGames(): String {
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date.toString()
        val response: HttpResponse = client.get("https://api-basketball.p.rapidapi.com/games?season=2024-2025&league=12&date=$currentDate") {
            headers {
                append("x-rapidapi-key", "9661b72e89mshff56832dcc17aacp12e8aejsn6b2fb754e77e")
                append("x-rapidapi-host", "api-basketball.p.rapidapi.com")
            }
        }
        return response.bodyAsText()
    }

    fun close() {
        client.close()
    }
}