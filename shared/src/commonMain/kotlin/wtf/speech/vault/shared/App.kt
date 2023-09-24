package wtf.speech.vault.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import wtf.speech.compass.core.ScreenContainer
import wtf.speech.compass.core.graph.Screen
import wtf.speech.compass.core.rememberRouter

@Composable
fun App() {
    val root1 = Example()
    val root2 = Example1()
    val router = rememberRouter(::getScreenById, root1)

    Column {
        TextButton(onClick = { router.replaceRoot(root2) }) {
            Text("Replace root1")
        }
        TextButton(onClick = { router.replaceRoot(root1) }) {
            Text("Replace root2")
        }

        ScreenContainer(router)
    }
}

fun getScreenById(id: Screen.Id): Screen {
    return when (id) {
        Example.Id -> Example()
        Example1.Id -> Example1()
        Details.Id -> Details()
        Details1.Id -> Details1()
        else -> throw IllegalArgumentException("Unknown screen id: $id")
    }
}