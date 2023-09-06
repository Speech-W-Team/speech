package wtf.speech.features.wallet.domain.models.blockchains

import wtf.speech.features.wallet.domain.generator.EthereumAddressGenerator
import wtf.speech.features.wallet.domain.generator.EthereumKeyGenerator
import wtf.speech.features.wallet.domain.generator.KeyGenerator
import wtf.speech.features.wallet.domain.models.CurveType

abstract class BinanceSmartChainBlockchain(
    id: String,
    fullName: String,
    baseUrl: String,
    isTestnet: Boolean = false
) : Blockchain(
    id,
    fullName,
    baseUrl,
    decimals = 18,
    currency = "BNB",
    curveType = CurveType.SECP256K1,
    isTestnet = isTestnet
)

internal object BinanceMainnetSmartChainBlockchain : BinanceSmartChainBlockchain(
    id = "BINANCE",
    fullName = "BNB Beacon Chain",
    baseUrl = "https://mainnet.binance.org"
)

internal object BinanceTestnetSmartChainBlockchain : BinanceSmartChainBlockchain(
    id = "BINANCE/test",
    fullName = "BNB Beacon Chain Testnet",
    baseUrl = "https://testnet.binance.org",
    isTestnet = true
)

data object BinanceSmartChainBlockchainProvider :
    BlockchainProvider<BinanceSmartChainBlockchain>() {
    override fun get(isTestnet: Boolean): BinanceSmartChainBlockchain =
        if (isTestnet) BinanceTestnetSmartChainBlockchain else BinanceMainnetSmartChainBlockchain

    override fun getKeyGenerator(): KeyGenerator = EthereumKeyGenerator

    override fun getAddressGenerator(): wtf.speech.features.wallet.domain.generator.AddressGenerator = EthereumAddressGenerator
}
