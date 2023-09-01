package wtf.speech.vault.crypto.domain.models.new

import wtf.speech.features.crypto.domain.models.new.Blockchain
import wtf.speech.features.crypto.domain.models.new.BlockchainProvider

object BitcoinMainnetBlockchain : Blockchain(
    id = "BTC",
    fullName = "Bitcoin",
    baseUrl = "https://blockchain.info",
    decimals = 8,
    currency = "BTC",
    curveType = CurveType.SECP256K1
)

object BitcoinTestnetBlockchain : Blockchain(
    id = "BTC/test",
    fullName = "Bitcoin Testnet",
    baseUrl = "https://testnet.blockchain.info",
    decimals = 8,
    currency = "BTC",
    curveType = CurveType.SECP256K1,
    isTestnet = true
)

object BitcoinBlockchainProvider : BlockchainProvider() {
    override fun get(isTestnet: Boolean): Blockchain = if (isTestnet) BitcoinTestnetBlockchain else BitcoinMainnetBlockchain
}




