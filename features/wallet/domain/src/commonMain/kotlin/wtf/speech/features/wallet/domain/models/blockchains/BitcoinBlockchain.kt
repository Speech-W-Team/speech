package wtf.speech.features.wallet.domain.models.blockchains

import wtf.speech.features.wallet.domain.generator.AddressGenerator
import wtf.speech.features.wallet.domain.generator.BitcoinAddressGenerator
import wtf.speech.features.wallet.domain.generator.BitcoinKeyGenerator
import wtf.speech.features.wallet.domain.generator.KeyGenerator
import wtf.speech.features.wallet.domain.models.CurveType


abstract class BitcoinBlockchain(
    id: String,
    fullName: String,
    baseUrl: String,
    isTestnet: Boolean = false
) : Blockchain(
    id,
    fullName,
    baseUrl,
    decimals = 8,
    currency = "BTC",
    curveType = CurveType.SECP256K1,
    isTestnet = isTestnet
)

internal object BitcoinMainnetBlockchain : BitcoinBlockchain(
    id = "BTC",
    fullName = "Bitcoin",
    baseUrl = "https://blockchain.info",
)

internal object BitcoinTestnetBlockchain : BitcoinBlockchain(
    id = "BTC/test",
    fullName = "Bitcoin Testnet",
    baseUrl = "https://testnet.blockchain.info",
    isTestnet = true
)

data object BitcoinBlockchainProvider : BlockchainProvider<BitcoinBlockchain>() {
    override fun get(isTestnet: Boolean): BitcoinBlockchain =
        if (isTestnet) BitcoinTestnetBlockchain else BitcoinMainnetBlockchain

    override fun getKeyGenerator(): KeyGenerator = BitcoinKeyGenerator

    override fun getAddressGenerator(): AddressGenerator = BitcoinAddressGenerator
}