package wtf.speech.vault.crypto.domain.models.new

import wtf.speech.vault.crypto.domain.models.CurveType

object BinanceMainnetBlockchain : Blockchain(
    id = "BINANCE",
    fullName = "BNB Beacon Chain",
    baseUrl = "https://mainnet.binance.org",
    decimals = 18,
    currency = "BNB",
    curveType = CurveType.SECP256K1
)

object BinanceTestnetBlockchain : Blockchain(
    id = "BINANCE/test",
    fullName = "BNB Beacon Chain Testnet",
    baseUrl = "https://testnet.binance.org",
    decimals = 18,
    currency = "BNB",
    curveType = CurveType.SECP256K1,
    isTestnet = true
)

object BinanceBlockchainProvider: BlockchainProvider() {
    override fun get(isTestnet: Boolean): Blockchain = if (isTestnet) BinanceTestnetBlockchain else BinanceMainnetBlockchain
}




