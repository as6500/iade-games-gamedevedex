package pt.iade.games.gamedevedex.ui.components

import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import pt.iade.games.gamedevedex.ProjectDetailActivity
import pt.iade.games.gamedevedex.R
import pt.iade.games.gamedevedex.models.Project
import pt.iade.games.gamedevedex.models.Student
import java.net.URI

@Composable
fun ProjectCard(
    project: Project,
    sharedPreferences: SharedPreferences,
    modifier: Modifier = Modifier,
    onVoteUpdated: (Int) -> Unit = {}
) {
    val context = LocalContext.current
    // var votes = project.votes
    var votes by remember { mutableStateOf(sharedPreferences.getInt("project_votes_${project.id}", project.votes)) }

    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = {
            Toast.makeText(context, project.title,
                Toast.LENGTH_SHORT).show()
            val intent = Intent(context, ProjectDetailActivity::class.java)
            context.startActivity(intent)
        }
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.therumblelogo),
                contentDescription = "Project Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                Text(
                    text = "$votes",
                    fontSize = 17.sp,
                    color = Color(0, 0, 0)
                )
            }
        }

        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = project.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.weight(weight = 1f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Button(
                onClick = {
                    votes++
                    sharedPreferences.edit().putInt("project_votes_${project.id}", votes).apply()
                    onVoteUpdated(votes)
                },
                modifier = Modifier.padding(start = 30.dp)
            ) {
                Text("Vote")
            }
        }
    }
}