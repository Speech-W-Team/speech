package wtf.speech.vault.crypto.domain.models

import com.ionspin.kotlin.bignum.decimal.BigDecimal

data class Amount(
    val currencySymbol: String,
    val value: BigDecimal? = null,
    val decimals: Int,
    val type: AmountType = AmountType.Coin
) {

    constructor(
        value: BigDecimal?,
        blockchain: Blockchain,
        type: AmountType = AmountType.Coin
    ) : this(blockchain.currency, value, blockchain.decimals, type)

    constructor(token: Token, value: BigDecimal? = null) :
            this(token.symbol, value, token.decimals, AmountType.AmountToken(token))

    constructor(amount: Amount, value: BigDecimal) :
            this(amount.currencySymbol, value, amount.decimals, amount.type)

    constructor(blockchain: Blockchain) :
            this(blockchain.currency, BigDecimal.ZERO, blockchain.decimals, AmountType.Coin)

    val longValue: Long?
        get() = value?.moveDecimalPoint(decimals)?.longValue()

    operator fun plus(add: BigDecimal): Amount =
        copy(value = (value ?: BigDecimal.ZERO).plus(add))

    operator fun minus(extract: BigDecimal): Amount =
        copy(value = (value ?: BigDecimal.ZERO).minus(extract))

}

sealed class AmountType {
    data object Coin : AmountType()
    data object Reserve : AmountType()
    data class AmountToken(val token: Token) : AmountType()
}