package wtf.speech.crypto.domain.models

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Represents the essential details of a cryptocurrency.
 *
 * @property id A unique identifier for the cryptocurrency.
 * @property name The full name of the cryptocurrency.
 * @property symbol The symbol representing the cryptocurrency.
 * @property logoUrl The URL link to the cryptocurrency's logo.
 * @property currentPrice The current price of the cryptocurrency in USD.
 * @property additionalInfo Optional information or description about the cryptocurrency.
 */
@Serializable
data class CryptoCurrencyInfo(
    val id: String,
    val name: String,
    val symbol: String,
    val logoUrl: String,
//    @Contextual val currentPrice: BigDecimal,
    val additionalInfo: String? = null
)
