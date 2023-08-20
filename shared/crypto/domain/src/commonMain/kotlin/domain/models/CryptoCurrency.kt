package domain.models


/**
 * A class for storing data about a cryptocurrency
 * @property name the name of the cryptocurrency
 * @property symbol the symbol of the cryptocurrency
 * @property price the price of the cryptocurrency in US dollars
 * @property change the change of the price in the last 24 hours in percentage
 */
data class CryptoCurrency(
    val name: String,
    val symbol: String,
    val price: Double,
    val change: Double
)