package wtf.speech.feature.wallet.ui.models

import androidx.compose.runtime.Stable
import com.ionspin.kotlin.bignum.decimal.BigDecimal

@Stable
data class WalletUI(
    val id: String,
    val amount: BigDecimal,
    val currency: String,
    val name: String,
    val amountFiat: BigDecimal,
    val currencyFiat: String,
)