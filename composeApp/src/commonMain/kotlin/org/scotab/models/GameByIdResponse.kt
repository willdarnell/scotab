package org.scotab.models

import kotlinx.serialization.Serializable

@Serializable
data class GameByIdResponse(
    val game: Map<String, Int>,
    val team: Map<String, Int>,
    val field_goals: Map<String, Int>,
    val threepoint_goals: Map<String, Int>,
    val freethrows_goals: Map<String, Int>,
    val rebounds: Map<String, Int>,
    val assists: Int,
    val steals: Int,
    val blocks: Int,
    val turnovers: Int,
    val personal_fouls: Int
)
