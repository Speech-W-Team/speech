package wtf.speech.vault.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import wtf.speech.compass.core.ScreenContainer
import wtf.speech.compass.core.rememberRouter
import wtf.speech.vault.app.example.Example
import wtf.speech.vault.app.example.Example1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val root1 = Example()
            val root2 = Example1()
            val router = rememberRouter(root1)

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
    }
}
