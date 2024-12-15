package pt.iade.games.gamedevedex.ui.components

import pt.iade.games.gamedevedex.R
import pt.iade.games.gamedevedex.models.Project
import pt.iade.games.gamedevedex.models.Student
import java.net.URI

fun projectTheRumble(votes: Int): Project {
    return Project(
        title = "The Rumble",
        votes = votes,
        description = "A very good description",
        id = 405,
        semester = 3,
        assets = listOf(
            R.drawable.therumblelogo
        ),
        groupMembers = listOf(
            Student(
                id = 123,
                name = "Sofia Sousa",
                biography = "Love playing Valorant. Currently thinking of switching courses.",
                mood = "Tired",
                avatar = R.drawable.sofiaicon
            ),
            Student(
                id = 124,
                name = "Mafalda Martins",
                biography = "Love playing Valorant. Currently thinking of switching courses.",
                mood = "Lucky",
                avatar = R.drawable.mafaldaicon
            ),
            Student(
                id = 125,
                name = "Tomás Lopes",
                biography = "Love playing Valorant. Currently thinking of switching courses.",
                mood = "Lucky",
                avatar = R.drawable.tomasicon
            )
        )
    )
}
