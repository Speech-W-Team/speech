package wtf.speech.vault.crypto.domain.models.blockchains

import wtf.speech.vault.crypto.domain.models.CurveType
import wtf.speech.vault.crypto.domain.generator.AddressGenerator
import wtf.speech.vault.crypto.domain.generator.EthereumAddressGenerator
import wtf.speech.vault.crypto.domain.generator.EthereumKeyGenerator
import wtf.speech.vault.crypto.domain.generator.KeyGenerator

abstract class EthereumBlockchain(
    id: String,
    fullName: String,
    baseUrl: String,
    isTestnet: Boolean = false
) : Blockchain(
    id,
    fullName,
    baseUrl,
    decimals = 18,
    currency = "ETH",
    curveType = CurveType.SECP256K1,
    isTestnet = isTestnet
)

internal object EthereumMainnetBlockchain : EthereumBlockchain(
    id = "ETH",
    fullName = "Ethereum",
    baseUrl = "https://etherscan.io",
)

internal object EthereumTestnetBlockchain : EthereumBlockchain(
    id = "ETH/test",
    fullName = "Ethereum Testnet",
    baseUrl = "https://ropsten.etherscan.io",
    isTestnet = true
)

data object EthereumBlockchainProvider : BlockchainProvider<EthereumBlockchain>() {
    override fun get(isTestnet: Boolean): EthereumBlockchain = if (isTestnet) EthereumTestnetBlockchain else EthereumMainnetBlockchain

    override fun getKeyGenerator(): KeyGenerator = EthereumKeyGenerator

    override fun getAddressGenerator(): AddressGenerator = EthereumAddressGenerator
}




