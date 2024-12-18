import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.scotab.models.Game

@Composable
fun GameItem(game: Game, onClick: (Int) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick(game.id) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Game ID: ${game.id}", style = MaterialTheme.typography.h6)
            Text(text = "League: ${game.league}", style = MaterialTheme.typography.body1)
            Text(text = "Season: ${game.season}", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Home Team: ${game.teams["home"]?.get("name")}", style = MaterialTheme.typography.body1)
            Text(text = "Home Score: ${game.scores["home"]?.get("points")}", style = MaterialTheme.typography.body1)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Visitor Team: ${game.teams["visitors"]?.get("name")}", style = MaterialTheme.typography.body1)
            Text(text = "Visitor Score: ${game.scores["visitors"]?.get("points")}", style = MaterialTheme.typography.body1)
        }
    }
}