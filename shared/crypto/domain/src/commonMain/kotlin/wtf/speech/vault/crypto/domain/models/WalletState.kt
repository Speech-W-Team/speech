package wtf.speech.vault.crypto.domain.models

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import wtf.speech.vault.crypto.domain.models.blockchains.Blockchain
import wtf.speech.vault.crypto.domain.models.transaction.Transaction

data class WalletState(
    val blockchain: Blockchain,
    val balance: BigDecimal,
    val transactions: List<Transaction>
)