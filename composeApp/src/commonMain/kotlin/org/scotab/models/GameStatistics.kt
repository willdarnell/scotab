package org.scotab.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

@Serializable
data class GameStatistics(
    val team: Map<String, String>,
    val statistics: Map<String, String?>
)

const val stubbedGameStats: String =
    """
    [{"team":{"id":"25","name":"Oklahoma City Thunder","nickname":"Thunder","code":"OKC","logo":"https://upload.wikimedia.org/wikipedia/fr/thumb/4/4f/Thunder_d%27Oklahoma_City_logo.svg/1200px-Thunder_d%27Oklahoma_City_logo.svg.png"},"statistics":"{\"fastBreakPoints\":null,\"pointsInPaint\":null,\"biggestLead\":null,\"secondChancePoints\":null,\"pointsOffTurnovers\":null,\"longestRun\":null,\"points\":81,\"fgm\":29,\"fga\":86,\"fgp\":\"33.7\",\"ftm\":18,\"fta\":22,\"ftp\":\"81.8\",\"tpm\":5,\"tpa\":32,\"tpp\":\"15.6\",\"offReb\":7,\"defReb\":36,\"totReb\":43,\"assists\":13,\"pFouls\":14,\"steals\":10,\"turnovers\":10,\"blocks\":5,\"plusMinus\":\"-16\",\"min\":\"240:00\"}"},{"team":{"id":"21","name":"Milwaukee Bucks","nickname":"Bucks","code":"MIL","logo":"https://upload.wikimedia.org/wikipedia/fr/3/34/Bucks2015.png"},"statistics":"{\"fastBreakPoints\":null,\"pointsInPaint\":null,\"biggestLead\":null,\"secondChancePoints\":null,\"pointsOffTurnovers\":null,\"longestRun\":null,\"points\":97,\"fgm\":34,\"fga\":81,\"fgp\":\"81.8\",\"ftm\":12,\"fta\":18,\"ftp\":\"66.7\",\"tpm\":17,\"tpa\":40,\"tpp\":\"42.5\",\"offReb\":9,\"defReb\":43,\"totReb\":52,\"assists\":25,\"pFouls\":20,\"steals\":6,\"turnovers\":19,\"blocks\":4,\"plusMinus\":\"16\",\"min\":\"240:00\"}"}]
    """
