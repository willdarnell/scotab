package org.scotab.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

object Sanitize {
    fun sanitizeResponse(jsonString: String): String {
        val jsonElement = Json.parseToJsonElement(jsonString)
        val jsonArray = jsonElement.jsonArray

        val sanitizedArray = jsonArray.map { gameElement ->
            val gameObject = gameElement.jsonObject.toMutableMap()

            JsonObject(replaceNulls(gameObject))
        }

        return Json.encodeToString(JsonArray(sanitizedArray))
    }

    private fun replaceNulls(map: MutableMap<String, JsonElement>): MutableMap<String, JsonElement> {
        val keysToRemove = mutableListOf<String>()
        map.forEach { (key, value) ->
            if (key in listOf("series", "officials", "timesTied", "leadChanges", "nugget")) {
                keysToRemove.add(key)
            } else {
                when (value) {
                    is JsonObject -> map[key] = JsonObject(replaceNulls(value.toMutableMap()))
                    is JsonArray -> map[key] = JsonPrimitive(value.joinToString(",") { it.toString() })
                    is JsonPrimitive -> map[key] = JsonPrimitive(value.contentOrNull ?: value.toString())
                    else -> map[key] = JsonPrimitive(value.toString())
                }
            }
        }
        keysToRemove.forEach { map.remove(it) }
        return map
    }
}