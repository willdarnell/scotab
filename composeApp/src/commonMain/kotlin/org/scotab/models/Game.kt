package org.scotab.models
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer

@Serializable
data class Game(
    val id: Int,
    val date: String,
    val time: String,
    val timestamp: Long,
    val timezone: String,
    val stage: String?,
    val week: String?,
    val venue: String?,
    val status: Map<String, String>,
    val league: Map<String, String>,
    val country: Map<String, String>,
    val teams: Map<String, Map<String, String>>,
    val scores: Map<String, Map<String, Int>>,

    )

@Serializable
data class Status(
    val long: String,
    val short: String,
    val timer: String?
)

@Serializable
data class League(
    val id: Int,
    val name: String,
    val type: String,
    val season: String,
    val logo: String
)

@Serializable
data class Country(
    val id: Int,
    val name: String,
    val code: String,
    val flag: String
)

@Serializable
data class Teams(
    val home: Team,
    val away: Team
)

@Serializable
data class Team(
    val id: Int,
    val name: String,
    val logo: String
)

@Serializable
data class Scores(
    val home: Score,
    val away: Score
)

@Serializable
data class Score(
    val quarter_1: Int?,
    val quarter_2: Int?,
    val quarter_3: Int?,
    val quarter_4: Int?,
    val over_time: Int?,
    val total: Int?
)