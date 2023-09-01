import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import wtf.speech.vault.shared.App

@Composable
fun MainView() = CryptoScreenPreview()

/**
 * A function for displaying the preview version of the screen with cryptocurrencies.
 */
@Composable
@Preview
fun CryptoScreenPreview() {
    App()
}
