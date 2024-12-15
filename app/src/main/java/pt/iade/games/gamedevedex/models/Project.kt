package pt.iade.games.gamedevedex.models

import coil3.Image
import java.net.URI

data class Project(
    val id: Int,
    val title: String,
    var votes: Int,
    val assets: List<Int>,
    val description: String,
    val groupMembers: List<Student>,
    val semester: Int
)
