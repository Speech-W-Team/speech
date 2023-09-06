package wtf.speech.features.wallet.domain.repository

import wtf.speech.features.wallet.domain.models.Address
import wtf.speech.features.wallet.domain.models.Wallet

/**
 * Repository interface for cryptocurrency wallet operations.
 */
interface WalletRepository {
    suspend fun getWallets(): List<Wallet>

    suspend fun getWallet(address: Address): Wallet

    suspend fun storeWallet(wallet: Wallet)
}

enum class CryptoType {
    BTC, ETH, USDT, BNB, XRP
}