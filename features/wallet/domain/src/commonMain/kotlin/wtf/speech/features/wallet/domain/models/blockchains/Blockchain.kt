package wtf.speech.features.wallet.domain.models.blockchains

import wtf.speech.features.wallet.domain.generator.AddressGenerator
import wtf.speech.features.wallet.domain.generator.KeyGenerator
import wtf.speech.features.wallet.domain.models.CurveType

/**
 * A sealed class representing a blockchain.
 *
 * @property id The id of the blockchain.
 * @property fullName The full name of the blockchain.
 * @property baseUrl The base URL of the blockchain.
 * @property currency The currency of the blockchain.
 * @property decimals The number of decimals of the blockchain.
 * @property curveType The curve type of the blockchain.
 * @property isTestnet Whether the blockchain is a testnet or not.
 */
sealed class Blockchain(
    val id: String,
    val fullName: String,
    val baseUrl: String,
    val currency: String,
    val decimals: Int,
    val curveType: CurveType,
    val isTestnet: Boolean = false,
) {
    /**
     * Checks if this object is equal to another object.
     *
     * @param other The other object to compare with.
     * @return True if this object is equal to the other object, false otherwise.
     */
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Blockchain) return false

        if (id != other.id) return false
        if (fullName != other.fullName) return false
        if (baseUrl != other.baseUrl) return false
        if (currency != other.currency) return false
        if (decimals != other.decimals) return false
        if (curveType != other.curveType) return false
        return isTestnet == other.isTestnet
    }

    /**
     * Returns the hash code of this object.
     *
     * @return The hash code of this object.
     */
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + baseUrl.hashCode()
        result = 31 * result + currency.hashCode()
        result = 31 * result + decimals
        result = 31 * result + curveType.hashCode()
        result = 31 * result + isTestnet.hashCode()
        return result
    }
}

/**
 * A sealed class representing a blockchain provider.
 *
 * @param T The type of blockchain provided by this provider.
 */
sealed class BlockchainProvider<T : Blockchain> {

    /**
     * Returns a blockchain instance provided by this provider.
     *
     * @param isTestnet Whether to use testnet or not.
     */
    abstract fun get(isTestnet: Boolean = false): T

    /**
     * Returns a key generator instance provided by this provider.
     */
    abstract fun getKeyGenerator(): KeyGenerator

    /**
     * Returns an address generator instance provided by this provider.
     */
    abstract fun getAddressGenerator(): AddressGenerator

}
