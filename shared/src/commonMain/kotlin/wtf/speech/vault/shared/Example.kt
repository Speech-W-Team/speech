package wtf.speech.vault.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import wtf.speech.compass.core.LocalRouter
import wtf.speech.compass.core.graph.Screen

class Example : Screen(Id) {
    @Composable
    override fun Content() {
        val router = LocalRouter.current
        val coroutineScope = rememberCoroutineScope()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            Text("Example")
            Button(onClick = { coroutineScope.launch { router.push(Details()) } }) {
                Text("Click me")
            }
        }
    }

    companion object {
        val Id = Screen.Id("Example")
    }
}

class Details : Screen(Id) {
    @Composable
    override fun Content() {
        val router = LocalRouter.current
        val coroutineScope = rememberCoroutineScope()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red)
        ) {
            Text("Details")
            Button(onClick = { coroutineScope.launch { router.pop() } }) {
                Text("back")
            }
        }
    }

    companion object {
        val Id = Screen.Id("Details")
    }
}

class Example1 : Screen(Id) {
    @Composable
    override fun Content() {
        val router = LocalRouter.current
        val coroutineScope = rememberCoroutineScope()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Text("Example1")
            Button(onClick = { coroutineScope.launch { router.push(Details1()) } }) {
                Text("Click me")
            }
        }
    }

    companion object {
        val Id = Screen.Id("Example1")
    }
}

class Details1 : Screen(Id) {
    @Composable
    override fun Content() {
        val router = LocalRouter.current
        val coroutineScope = rememberCoroutineScope()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Text("Details1")
            Button(onClick = { coroutineScope.launch { router.pop() } }) {
                Text("back")
            }
        }
    }

    companion object {
        val Id = Screen.Id("Details1")
    }
}
