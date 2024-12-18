package org.scotab.models

import kotlinx.serialization.Serializable

@Serializable
data class Game(
    val id: Int,
    val league: String,
    val season: Int,
    val date: Map<String, String?>,
    val stage: Int,
    val status: Map<String, String?>, // Make status values nullable
    val periods: Map<String, String?>, // Make periods values nullable
    val arena: Map<String, String?>,
    val teams: Map<String, Map<String, String>>,
    val scores: Map<String, Map<String, String>>,
    val officials: List<String>,
    val timesTied: Int?,
    val leadChanges: Int?,
    val nugget: String?
)