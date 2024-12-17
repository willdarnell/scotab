import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.scotab.models.Game

@Composable
fun GameItem(game: Game, onClick: (Int) -> Unit) {
    val homeScore = game.scores["home"]?.get("total") ?: 0
    val awayScore = game.scores["away"]?.get("total") ?: 0
    val isFinal = game.status["short"] in listOf("FT", "AOT")
    val homeColor = if (homeScore > awayScore) Color(0xFF4CAF50) else Color(0xFFF44336)
    val awayColor = if (awayScore > homeScore) Color(0xFF4CAF50) else Color(0xFFF44336)
    val homeFontWeight = if (isFinal && homeScore > awayScore) FontWeight.Bold else FontWeight.Normal
    val awayFontWeight = if (isFinal && awayScore > homeScore) FontWeight.Bold else FontWeight.Normal

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick(game.id) },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.Black),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = game.teams["home"]?.get("name") ?: "Unknown",
                        fontSize = 18.sp,
                        fontWeight = homeFontWeight,
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = game.teams["away"]?.get("name") ?: "Unknown",
                        fontSize = 18.sp,
                        fontWeight = awayFontWeight,
                        color = MaterialTheme.colors.onSurface
                    )
                }
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = homeScore.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = homeColor
                    )
                    Text(
                        text = awayScore.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = awayColor
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(8.dp))
            if (isFinal) {
                Text(
                    text = "Final",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.primary
                )
            } else {
                val statusText = when (game.status["short"]) {
                    "NS" -> "Not Started"
                    "Q1" -> "Quarter 1"
                    "Q2" -> "Quarter 2"
                    "Q3" -> "Quarter 3"
                    "Q4" -> "Quarter 4"
                    "OT" -> "Over Time"
                    "BT" -> "Break Time"
                    "HT" -> "Halftime"
                    "POST" -> "Game Postponed"
                    "CANC" -> "Game Cancelled"
                    "SUSP" -> "Game Suspended"
                    "AWD" -> "Game Awarded"
                    "ABD" -> "Game Abandoned"
                    else -> "Unknown Status"
                }
                val timer = game.status["timer"]
                Text(
                    text = if (timer != null) "$statusText - $timer" else statusText,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.primary
                )
            }
        }
    }
}