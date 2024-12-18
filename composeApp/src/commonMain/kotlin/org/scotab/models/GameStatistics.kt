package org.scotab.models

import kotlinx.serialization.Serializable

@Serializable
data class GameStatistics(
    val id: Int,
    val name: String,
    val nickname: String,
    val code: String,
    val logo: String,
    val statistics: Map<String, String?>
)