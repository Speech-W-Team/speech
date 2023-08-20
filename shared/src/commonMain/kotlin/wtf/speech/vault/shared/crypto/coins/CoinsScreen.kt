package wtf.speech.vault.shared.crypto.coins

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.models.CryptoCurrency
import wtf.speech.shared.core.ui.BaseScreenContainer

/**
 * A function for displaying the screen with cryptocurrencies.
 * @param viewModel the ViewModel for the screen with cryptocurrencies.
 */
@Composable
fun CryptoScreen(viewModel: CryptoViewModel) {
    val state by viewModel.state.collectAsState()
    val effect by viewModel.effect.collectAsState(null)

    CryptoScreenContent(
        { viewModel.handleAction(CryptoScreenAction.LoadCryptos) },
        { viewModel.handleAction(CryptoScreenAction.SelectCrypto(it)) },
        state,
        effect
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CryptoScreenContent(
    onRetryListener: () -> Unit,
    onCryptoWalletClickListener: (CryptoCurrency) -> Unit,
    state: CryptoScreenState,
    effect: CryptoScreenEffect?
) {
    val accentColor = Color(0xFFE63946)
    val backgroundColor = Color(0xFFF1F6F9)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Криптовалюты") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Поиск")
                    }
                },
            )
        },
        content = { insets ->
            BaseScreenContainer(
                onScreenState = { CryptoWalletsList(insets, it, onCryptoWalletClickListener) },
                onErrorScreenState = { ErrorState(onRetryListener, it.message, accentColor) },
                onEffect = { },
                state = state,
                effect = effect
            )
        },
        containerColor = backgroundColor
    )
}

@Composable
private fun CryptoWalletsList(
    insets: PaddingValues,
    state: CryptoScreenState,
    onCryptoWalletClickListener: (CryptoCurrency) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(insets)) {
        items(state.cryptos) { crypto ->
            CryptoCard(crypto, onCryptoWalletClickListener)
        }
    }
}

@Composable
private fun ErrorState(onRetryListener: () -> Unit, errorMessage: String, accentColor: Color) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = errorMessage, color = accentColor, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onRetryListener,
                colors = ButtonDefaults.buttonColors(containerColor = accentColor)
            ) {
                Text(text = "Повторить", color = Color.White)
            }
        }
    }
}

@Composable
private fun LoadingState(accentColor: Color) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(color = accentColor)
    }
}

/**
 * A function for displaying a cryptocurrency card.
 * @param crypto the cryptocurrency to display on the card.
 * @param onClick the function to invoke when the card is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptoCard(crypto: CryptoCurrency, onClick: (CryptoCurrency) -> Unit) {
    val onClickListener = remember { { onClick(crypto) } }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        onClick = onClickListener
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.padding(16.dp).size(48.dp), contentAlignment = Alignment.Center) {
                Image(imageVector = Icons.Default.ArrowBack, contentDescription = crypto.name)
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = crypto.name, style = MaterialTheme.typography.headlineSmall)
                Text(text = "${crypto.price} USD", style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}
