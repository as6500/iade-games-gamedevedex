package pt.iade.games.gamedevedex.controllers

import android.util.Log
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.json.responseJson
import pt.iade.games.gamedevedex.models.Student
import java.net.URI

class StudentController {
    val serverBase = "http://10.0.2.2:8888"

    public fun GetById(
        id: Int,
        onSuccess: (student: Student) -> Unit,
        onFailure: () -> Unit = { }
    ) {
        Fuel.get("$serverBase/student?id=$id")
            // .response(function (request, response, result) {
            .responseJson { request, response, result ->
                println(request)
                println(response)
                val (json, error) = result
                if (json != null) {
                    onSuccess(Student(
                        id = id,
                        name = json.obj().getString("name"),
                        avatar = 1,
                        biography = json.obj().getString("biography"),
                        mood = json.obj().getString("mood")
                    ))
                } else {
                    Log.e("STUDENTCONTROL", "GetByID Failed")
                    onFailure()
                }
            }
            // });
    }
}