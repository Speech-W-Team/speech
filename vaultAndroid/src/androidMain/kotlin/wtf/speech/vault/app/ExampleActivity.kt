package wtf.speech.vault.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import wtf.speech.compass.core.ScreenContainer
import wtf.speech.compass.core.rememberRouter
import wtf.speech.vault.shared.Example
import wtf.speech.vault.shared.Example1
import wtf.speech.vault.shared.getScreenById

class ExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
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
                TextButton(onClick = { onBackPressed() }) {
                    Text("back to main activity")
                }

                ScreenContainer(router)
            }
        }
    }
}
