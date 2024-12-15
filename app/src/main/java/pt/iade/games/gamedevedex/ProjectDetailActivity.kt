package pt.iade.games.gamedevedex

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import pt.iade.games.gamedevedex.models.Project
import pt.iade.games.gamedevedex.models.Student
import pt.iade.games.gamedevedex.ui.theme.GamedevedexTheme
import java.net.URI

class ProjectDetailActivity : ComponentActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPreferences = getSharedPreferences("GamedevedexPreferences", Context.MODE_PRIVATE)

        val votes = sharedPreferences.getInt("project_votes", 0)

        setContent {
            GamedevedexTheme {
                ProjectDetailScreen(project = rumbleProject(votes = votes),
                    onVoteUpdated = { newVoteCount ->
                    saveVotesToPreferences(newVoteCount)
                })
            }
        }
    }
    fun saveVotesToPreferences(votes: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt("project_votes", votes)
        editor.apply()
    }
}



fun rumbleProject(votes: Int): Project {
    return Project(
        title = "The Rumble",
        votes = votes,
        description = "An amazing game project by talented students.",
        id = 1,
        semester = 3,
        assets = listOf(
            R.drawable.many,
            R.drawable.ziggy,
            R.drawable.daniel,
            R.drawable.suzi
        ),
        groupMembers = listOf(
            Student(
                id = 1,
                name = "Sofia Sousa",
                biography = "Loves playing Minecraft. Thinking of switching courses.",
                mood = "Cold",
                avatar = R.drawable.sofiaicon
            ),
            Student(
                id = 2,
                name = "Tomás Lopes",
                biography = "Enjoys coding and playing games.",
                mood = "Excited",
                avatar = R.drawable.tomasicon
            ),
            Student(
                id = 3,
                name = "Mafalda Martins",
                biography = "Enjoys coding and playing games.",
                mood = "Motivated",
                avatar = R.drawable.mafaldaicon
            )
        )
    )
}

@Composable
fun ProjectDetailScreen(project: Project, onVoteUpdated: (Int) -> Unit) {
    val scrollState = rememberScrollState()

    val currentAssetIndex = remember { mutableStateOf(0) }

    fun switchImage() {
        currentAssetIndex.value = (currentAssetIndex.value + 1) % project.assets.size
    }

    var voteCount = remember { mutableStateOf(project.votes) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
            .verticalScroll(scrollState)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(
                text = project.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.therumblelogo),
            contentDescription = "cover image of the rumble",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "${voteCount.value}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Votes",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        text = "Project Description",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = project.description,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Group Members",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        for (student in project.groupMembers) {
            StudentCard(student = student)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Project Assets",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.LightGray, RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = project.assets[currentAssetIndex.value]),
                contentDescription = "Asset Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Button(onClick = { switchImage() }) {
            Text("Switch Image")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "NPCs of the game",
            fontSize = 16.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun StudentCard(student: Student) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(end = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = student.avatar),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(1.dp, Color.Gray, CircleShape)
                )
                Text(
                    text = student.mood,
                    fontSize = 12.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = student.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = student.biography,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ProjectDetailScreenPreview() {
    val defaultVotes = 10
    GamedevedexTheme {
        ProjectDetailScreen(project = rumbleProject(votes = defaultVotes), onVoteUpdated = { newVoteCount -> println("New vote count: $newVoteCount")
        }
        )
    }
}
