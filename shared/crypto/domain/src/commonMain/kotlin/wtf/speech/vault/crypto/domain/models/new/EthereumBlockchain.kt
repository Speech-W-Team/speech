package wtf.speech.vault.crypto.domain.models.new

import wtf.speech.vault.crypto.domain.models.CurveType

object EthereumMainnetBlockchain : Blockchain(
    id = "ETH",
    fullName = "Ethereum",
    baseUrl = "https://etherscan.io",
    decimals = 18,
    currency = "ETH",
    curveType = CurveType.SECP256K1
)

object EthereumTestnetBlockchain : Blockchain(
    id = "ETH/test",
    fullName = "Ethereum Testnet",
    baseUrl = "https://ropsten.etherscan.io",
    decimals = 18,
    currency = "ETH",
    curveType = CurveType.SECP256K1,
    isTestnet = true
)

object EthereumBlockchainProvider : BlockchainProvider() {
    override fun get(isTestnet: Boolean): Blockchain = if (isTestnet) EthereumTestnetBlockchain else EthereumMainnetBlockchain
}




