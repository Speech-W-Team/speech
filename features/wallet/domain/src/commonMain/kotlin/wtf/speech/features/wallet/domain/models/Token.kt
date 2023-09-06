package wtf.speech.features.wallet.domain.models

/**
 * Represents a digital cryptocurrency token or coin.
 *
 * @property id An optional unique identifier for the token. This can be useful for databases or internal tracking.
 * @property name The full name of the token (e.g., "Ethereum").
 * @property symbol The ticker symbol for the token (e.g., "ETH").
 * @property contractAddress The smart contract address of the token on its respective blockchain.
 *                            This is especially relevant for tokens adhering to standards like ERC-20 or ERC-721.
 * @property decimals The number of decimals the token supports. This denotes the smallest fractional unit of the token.
 *                    For example, for Ether, it would be 18, meaning the smallest unit of Ether is 1x10^-18.
 *
 * @author Usman Akhmedov
 * @since 2023-08-22
 */
data class Token(
    val id: String? = null,
    val name: String,
    val symbol: String,
    val contractAddress: String,
    val decimals: Int,
)
