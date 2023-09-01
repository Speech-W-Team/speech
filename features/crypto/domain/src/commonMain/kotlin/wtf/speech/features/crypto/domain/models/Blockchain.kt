package wtf.speech.features.crypto.domain.models

enum class Blockchain(
    val currency: String,
    val decimals: Int,
    val curveType: CurveType,
    val mainNet: BlockchainNetworkSetup,
    val testNet: BlockchainNetworkSetup? = null,
) {

    BINANCE(
        decimals = 18,
        currency = "BNB",
        mainNet = BlockchainNetworkSetup(
            id = "BINANCE",
            fullName = "BNB Beacon Chain",
            baseUrl = "https://mainnet.binance.org",
        ),
        testNet = BlockchainNetworkSetup(
            id = "BINANCE/test",
            fullName = "BNB Beacon Chain Testnet",
            baseUrl = "https://testnet.binance.org",
            isTestnet = true
        ),
        curveType = CurveType.SECP256K1
    ),

    BITCOIN(
        decimals = 8,
        currency = "BTC",
        mainNet = BlockchainNetworkSetup(
            id = "BTC",
            fullName = "Bitcoin",
            baseUrl = "https://blockchain.info",
        ),
        testNet = BlockchainNetworkSetup(
            id = "BTC/test",
            fullName = "Bitcoin Testnet",
            baseUrl = "https://testnet.blockchain.info",
            isTestnet = true
        ),
        curveType = CurveType.SECP256K1
    ),

    ETHEREUM(
        decimals = 18,
        currency = "ETH",
        mainNet = BlockchainNetworkSetup(
            id = "ETH",
            fullName = "Ethereum",
            baseUrl = "https://etherscan.io",
        ),
        testNet = BlockchainNetworkSetup(
            id = "ETH/test",
            fullName = "Ethereum Testnet",
            baseUrl = "https://ropsten.etherscan.io",
            isTestnet = true
        ),
        curveType = CurveType.SECP256K1
    ),

    ETHEREUM_CLASSIC(
        decimals = 18,
        currency = "ETC",
        mainNet = BlockchainNetworkSetup(
            id = "ETC",
            fullName = "Ethereum Classic",
            baseUrl = "https://blockscout.com/etc/mainnet",
        ),
        testNet = BlockchainNetworkSetup(
            id = "ETC/test",
            fullName = "Ethereum Classic Testnet",
            baseUrl = "https://mordor.blockscout.com",
            isTestnet = true
        ),
        curveType = CurveType.SECP256K1
    ),

    LITECOIN(
        decimals = 8,
        currency = "LTC",
        mainNet = BlockchainNetworkSetup(
            id = "LTC",
            fullName = "Litecoin",
            baseUrl = "https://live.blockcypher.com/ltc",
        ),
        testNet = BlockchainNetworkSetup(
            id = "LTC/test",
            fullName = "Litecoin Testnet",
            baseUrl = "https://testnet.litecore.io",
            isTestnet = true
        ),
        curveType = CurveType.SECP256K1
    )
}