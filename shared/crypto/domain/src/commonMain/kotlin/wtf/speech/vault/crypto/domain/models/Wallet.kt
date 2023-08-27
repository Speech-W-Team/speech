package wtf.speech.vault.crypto.domain.models

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.shared.core.domain.models.PublicKey

/**
 * Represents a digital cryptocurrency wallet. This class encapsulates
 * details like the blockchain, associated addresses, public key, and supported tokens.
 *
 * @property blockchain The blockchain associated with this wallet.
 * @property addresses A set of addresses associated with this wallet.
 * @property publicKey The public key associated with this wallet.
 * @property tokens A set of tokens supported by this wallet.
 *
 * @author Usman Akhmedov
 * @since 2023-08-22
 */
class Wallet(
    val blockchain: Blockchain,
    val addresses: MutableSet<Address>,
    val publicKey: PublicKey,
    val privateKey: PrivateKey,
    tokens: MutableSet<Token>
) {
    private val _tokens: MutableSet<Token> = tokens

    /**
     * Returns an immutable view of the tokens supported by this wallet.
     * @return the tokens
     */
    fun getTokens(): Set<Token> = _tokens.toSet()

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
        _tokens.add(token)
    }

    /**
     * Checks if the wallet supports a specific token.
     * @param token the token to check
     * @return true if the wallet has the token, false otherwise
     */
    fun hasToken(token: Token): Boolean = _tokens.contains(token)

    /**
     * Returns the balance of a specific token.
     * @param token the token to check
     * @return the balance of the token
     */
    fun balanceOf(token: Token): BigDecimal {
        // Fetch balance from a source...
        return BigDecimal.ZERO
    }

    companion object {
        /**
         * Factory method to create a default wallet for a specific blockchain with a given public key.
         * @param blockchain the blockchain to associate with the wallet
         * @param publicKey the public key for the wallet
         * @return a new default wallet instance
         */
        fun defaultWallet(blockchain: Blockchain, privateKey: PrivateKey, publicKey: PublicKey): Wallet {
            return Wallet(blockchain, mutableSetOf(), publicKey, privateKey, mutableSetOf())
        }
    }
}