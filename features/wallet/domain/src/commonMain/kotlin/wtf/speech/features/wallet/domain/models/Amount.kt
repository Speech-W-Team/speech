package wtf.speech.features.wallet.domain.models

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import wtf.speech.features.wallet.domain.models.blockchains.Blockchain

/**
 * A data class representing an amount.
 *
 * @property currencySymbol The currency symbol of the amount.
 * @property value The value of the amount.
 * @property decimals The number of decimals of the amount.
 * @property type The type of the amount.
 */
data class Amount(
    val currencySymbol: String,
    val value: BigDecimal? = null,
    val decimals: Int,
    val type: AmountType = AmountType.Coin
) {

    /**
     * Constructor for creating an amount from a blockchain and a value.
     *
     * @param value The value of the amount.
     * @param blockchain The blockchain to use.
     * @param type The type of the amount.
     */
    constructor(
        value: BigDecimal?,
        blockchain: Blockchain,
        type: AmountType = AmountType.Coin
    ) : this(blockchain.currency, value, blockchain.decimals, type)

    /**
     * Constructor for creating an amount from a token and a value.
     *
     * @param token The token to use.
     * @param value The value of the amount.
     */
    constructor(token: Token, value: BigDecimal? = null) :
            this(token.symbol, value, token.decimals, AmountType.AmountToken(token))

    /**
     * Constructor for creating an amount from another amount and a value.
     *
     * @param amount The other amount to use.
     * @param value The value of the amount.
     */
    constructor(amount: Amount, value: BigDecimal) :
            this(amount.currencySymbol, value, amount.decimals, amount.type)

    /**
     * Constructor for creating an empty coin amount from a blockchain.
     *
     * @param blockchain The blockchain to use.
     */
    constructor(blockchain: Blockchain) :
            this(blockchain.currency, BigDecimal.ZERO, blockchain.decimals, AmountType.Coin)

    /**
     * Returns the long value of this amount.
     */
    val longValue: Long?
        get() = value?.moveDecimalPoint(decimals)?.longValue()

    /**
     * Adds a given value to this amount and returns a new instance with the result.
     *
     * @param add The value to add.
     */
    operator fun plus(add: BigDecimal): Amount =
        copy(value = (value ?: BigDecimal.ZERO).plus(add))

    /**
     * Subtracts a given value from this amount and returns a new instance with the result.
     *
     * @param extract The value to subtract.
     */
    operator fun minus(extract: BigDecimal): Amount =
        copy(value = (value ?: BigDecimal.ZERO).minus(extract))

}

/**
 * A sealed class representing the type of an amount.
 */
sealed class AmountType {
    /** Represents a coin. */
    data object Coin : AmountType()

    /** Represents a reserve. */
    data object Reserve : AmountType()

    /** Represents an amount token. */
    data class AmountToken(val token: Token) : AmountType()
}
