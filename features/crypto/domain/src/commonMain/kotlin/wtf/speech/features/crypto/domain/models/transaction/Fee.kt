package wtf.speech.features.crypto.domain.models.transaction

import com.ionspin.kotlin.bignum.integer.BigInteger
import wtf.speech.features.crypto.domain.models.Amount

sealed class Fee(open val amount: Amount) {

    data class Ethereum(
        override val amount: Amount,
        val gasLimit: BigInteger,
        val gasPrice: BigInteger,
    ) : Fee(amount)

    data class Common(override val amount: Amount) : Fee(amount)

}