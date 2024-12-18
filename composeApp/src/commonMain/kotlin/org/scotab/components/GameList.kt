import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.scotab.models.Game

@Composable
fun GameList(games: List<Game>, onGameClick: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(games) { game ->
            GameItem(game, onClick = onGameClick)
        }
    }
}