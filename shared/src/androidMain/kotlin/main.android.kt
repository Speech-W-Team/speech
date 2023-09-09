import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import wtf.speech.features.home.ui.HomeScreen
import wtf.speech.features.home.ui.HomeViewModel

@Composable
fun MainView() = CryptoScreenPreview()

/**
 * A function for displaying the preview version of the screen with cryptocurrencies.
 */
@Composable
@Preview
fun CryptoScreenPreview() {
    HomeScreen(HomeViewModel())
}
