package wtf.speech.vault.app

import android.content.Intent
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val root1 = Example()
            val root2 = Example1()
            val router = rememberRouter(::getScreenById, root1)

            val intent = Intent(this, ExampleActivity::class.java)
            Column {
                TextButton(onClick = { router.replaceRoot(root2) }) {
                    Text("Replace to root 2")
                }
                TextButton(onClick = { router.replaceRoot(root1) }) {
                    Text("Replace to root1")
                }
                TextButton(onClick = { startActivity(intent) }) {
                    Text("Start new activity")
                }

                ScreenContainer(router)
            }
        }
    }
}
