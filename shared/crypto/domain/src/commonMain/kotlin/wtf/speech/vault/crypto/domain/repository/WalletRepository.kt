package wtf.speech.vault.crypto.domain.repository

import wtf.speech.vault.crypto.domain.models.Wallet

/**
 * Repository interface for cryptocurrency wallet operations.
 */
interface WalletRepository {
    fun getWallet(cryptoType: CryptoType): Wallet

    fun get
    fun saveWallet(crypto: Wallet)
}

enum class CryptoType {
    BTC, ETH, USDT, BNB, XRP
}