package org.scotab.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

object Sanitize {
    fun sanitizeResponse(jsonString: String): String {
        val jsonElement = Json.parseToJsonElement(jsonString)
        val jsonArray = jsonElement.jsonArray

        val sanitizedArray = jsonArray.map { gameElement ->
            val gameObject = gameElement.jsonObject.toMutableMap()

            // Transform league id
            val league = gameObject["league"]?.jsonObject?.toMutableMap()
            league?.set("id", JsonPrimitive(league["id"]?.toString() ?: ""))
            gameObject["league"] = JsonObject(league?.let { replaceNulls(it) } ?: emptyMap())

            // Transform country id
            val country = gameObject["country"]?.jsonObject?.toMutableMap()
            country?.set("id", JsonPrimitive(country["id"]?.toString() ?: ""))
            gameObject["country"] = JsonObject(country?.let { replaceNulls(it) } ?: emptyMap())

            // Transform teams ids
            val teams = gameObject["teams"]?.jsonObject?.toMutableMap()
            teams?.forEach { (key, teamElement) ->
                val team = teamElement.jsonObject.toMutableMap()
                team["id"] = JsonPrimitive(team["id"]?.toString() ?: "")
                teams[key] = JsonObject(replaceNulls(team))
            }
            gameObject["teams"] = JsonObject(teams ?: emptyMap())

            // Replace null values with empty strings
            JsonObject(replaceNulls(gameObject))
        }

        return Json.encodeToString(JsonArray(sanitizedArray))
    }

    private fun replaceNulls(map: MutableMap<String, JsonElement>): MutableMap<String, JsonElement> {
        val intKeys = setOf(
            "quarter_1",
            "quarter_2",
            "quarter_3",
            "quarter_4",
            "over_time",
            "total"
        )
        map.forEach { (key, value) ->
            when (value) {
                is JsonObject -> map[key] = JsonObject(replaceNulls(value.toMutableMap()))
                is JsonArray -> map[key] = JsonArray(value.map { element ->
                    if (element is JsonObject) JsonObject(replaceNulls(element.toMutableMap())) else element
                })
                is JsonPrimitive -> if (value.contentOrNull == null) {
                    if (key in intKeys)
                        map[key] = JsonPrimitive(0)
                    else
                        map[key] = JsonPrimitive("")
                }
            }
        }
        return map
    }
}