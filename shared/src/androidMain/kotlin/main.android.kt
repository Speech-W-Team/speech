import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import wtf.speech.vault.shared.crypto.coins.CryptoScreenContent
import wtf.speech.vault.shared.crypto.coins.CryptoScreenState

@Composable
fun MainView() = CryptoScreenPreview()

/**
 * A function for displaying the preview version of the screen with cryptocurrencies.
 */
@Composable
@Preview
fun CryptoScreenPreview() {
    CryptoScreenContent({}, {}, CryptoScreenState.preview, null)
}
