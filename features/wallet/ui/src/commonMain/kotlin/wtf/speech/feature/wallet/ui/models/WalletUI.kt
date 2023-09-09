package wtf.speech.feature.wallet.ui.models

import androidx.compose.runtime.Stable
import com.ionspin.kotlin.bignum.decimal.BigDecimal

@Stable
data class WalletUI(
    val address: String,
    val amount: BigDecimal,
    val currency: String,
    val name: String,
    val amountFiat: BigDecimal,
    val currencyFiat: String,
) {
    companion object {
        val preview = WalletUI(
            address = "",
            amount = BigDecimal.fromDouble(103.123123),
            currency = "ETH",
            name = "Ethereum",
            amountFiat = BigDecimal.fromDouble(23.12),
            currencyFiat = "USD",
        )
    }
}