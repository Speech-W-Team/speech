package wtf.speech.vault.crypto.domain.models

/**
 * Represents the type of a blockchain network, allowing differentiation between mainnet and testnet environments.
 *
 * A blockchain typically has a main network (mainnet) where real transactions occur and one or more
 * test networks (testnets) used primarily for testing purposes.
 *
 * @property id A unique identifier for the network type. Typically an internal reference.
 * @property fullName The full descriptive name of the network (e.g., "Ethereum MainNet").
 * @property baseUrl The base URL for accessing blockchain data and services.
 *
 * @author Usman Akhmedov
 * @since 2023-08-23
 */
data class BlockchainNetworkSetup(
    val id: String,
    val fullName: String,
    val baseUrl: String,
    val isTestnet: Boolean = false,
)