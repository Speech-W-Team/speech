package wtf.speech.vault.shared

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import wtf.speech.core.design.themes.SpeechTheme
import wtf.speech.feature.passcode.ui.create.CreatePasscodeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    SpeechTheme {
        Scaffold {
            CreatePasscodeScreen().Content()
        }
    }
}
