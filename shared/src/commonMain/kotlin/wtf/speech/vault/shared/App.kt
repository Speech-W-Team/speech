package wtf.speech.vault.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.ktor.utils.io.core.toByteArray
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import wtf.speech.compose.kit.texts.HeadlineLargeText
import wtf.speech.compose.kit.themes.SpeechTheme
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.usecases.BitcoinAddressGenerator

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun App() {
    SpeechTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { HeadlineLargeText("Vault") }
                )
            }
        ) {
            var greetingText by remember { mutableStateOf("Hello, World!") }
            var showImage by remember { mutableStateOf(false) }

            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(it),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    greetingText = "Hello, Vault"
                    showImage = !showImage
                }) {
                    Text(greetingText)
                }
                AnimatedVisibility(showImage) {
                    Image(
                        painterResource("compose-multiplatform.xml"),
                        null
                    )
                }
            }
        }
    }
}
