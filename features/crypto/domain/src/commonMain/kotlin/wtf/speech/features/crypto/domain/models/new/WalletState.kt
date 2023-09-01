package wtf.speech.features.crypto.domain.models.new

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import wtf.speech.features.crypto.domain.models.transaction.Transaction

data class WalletState(
    val blockchain: Blockchain,
    val balance: BigDecimal,
    val transactions: List<Transaction>
)