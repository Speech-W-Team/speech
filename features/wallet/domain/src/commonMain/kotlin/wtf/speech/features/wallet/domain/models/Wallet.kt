package wtf.speech.features.wallet.domain.models

import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.models.PublicKey
import wtf.speech.features.wallet.domain.models.blockchains.Blockchain

/**
 * Represents a digital cryptocurrency wallet. This class encapsulates
 * details like the blockchain, associated addresses, public key, and supported tokens.
 *
 * @property blockchain The blockchain associated with this wallet.
 * @property addresses A set of addresses associated with this wallet.
 * @property publicKey The public key associated with this wallet.
 * @property getCurrentTokens A set of tokens supported by this wallet.
 *
 * @author Usman Akhmedov
 * @since 2023-08-22
 */
open class Wallet(
    open val blockchain: Blockchain,
    open val addresses: MutableSet<Address>,
    open val publicKey: PublicKey,
    protected open val tokens: MutableSet<Token>
) {

    /**
     * Returns an immutable view of the tokens supported by this wallet.
     * @return the tokens
     */
    fun getCurrentTokens(): Set<Token> = tokens.toSet()

    /**
     * Adds a new address to the wallet.
     * @param address the address to add
     */
    fun addAddress(address: Address) {
        addresses.add(address)
    }

    /**
     * Adds a new token to the wallet.
     * @param token the token to add
     */
    fun addToken(token: Token) {
        tokens.add(token)
    }

    /**
     * Checks if the wallet supports a specific token.
     * @param token the token to check
     * @return true if the wallet has the token, false otherwise
     */
    fun hasToken(token: Token): Boolean = tokens.contains(token)

    /**
     * Returns the balance of a specific token.
     * @param token the token to check
     * @return the balance of the token
     */
//    fun balanceOf(token: Token): BigDecimal {
//        // Fetch balance from a source...
//        return BigDecimal.ZERO
//    }

    companion object {
        /**
         * Factory method to create a default wallet for a specific blockchain with a given public key.
         * @param blockchain the blockchain to associate with the wallet
         * @param publicKey the public key for the wallet
         * @return a new default wallet instance
         */
        fun defaultWallet(blockchain: Blockchain, publicKey: PublicKey): Wallet {
            return Wallet(blockchain, mutableSetOf(), publicKey, mutableSetOf())
        }
    }
}

data class ExtendedWallet(
    override val blockchain: Blockchain,
    override val addresses: MutableSet<Address>,
    override val publicKey: PublicKey,
    override val tokens: MutableSet<Token>,
    val privateKey: PrivateKey,
) : Wallet(blockchain, addresses, publicKey, tokens) {
    companion object {
        /**
         * Factory method to create a default wallet for a specific blockchain with a given public key.
         *
         * @param blockchain the blockchain to associate with the wallet
         * @param publicKey the public key for the wallet
         * @param privateKey the private key for the wallet
         *
         * @return a new default wallet instance
         */
        fun defaultWallet(blockchain: Blockchain, publicKey: PublicKey, privateKey: PrivateKey): ExtendedWallet {
            return ExtendedWallet(blockchain, mutableSetOf(), publicKey, mutableSetOf(), privateKey)
        }

        /**
         * Factory method to create a default wallet for a specific blockchain with a given public key.
         *
         * @param blockchain the blockchain to associate with the wallet
         * @param publicKey the public key for the wallet
         * @param privateKey the private key for the wallet
         *
         * @return a new default wallet instance
         */
        fun defaultWallet(blockchain: Blockchain, publicKey: PublicKey, privateKey: PrivateKey, address: Address): ExtendedWallet {
            return ExtendedWallet(blockchain, mutableSetOf(address), publicKey, mutableSetOf(), privateKey)
        }
    }
}