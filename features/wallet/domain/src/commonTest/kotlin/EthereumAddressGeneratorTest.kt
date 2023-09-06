import kotlinx.coroutines.runBlocking
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.features.wallet.domain.generator.EthereumAddressGenerator
import wtf.speech.features.wallet.domain.generator.WalletFactory
import wtf.speech.features.wallet.domain.models.blockchains.EthereumBlockchainProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EthereumAddressGeneratorTest {
    private val walletFactory = WalletFactory.create(EthereumBlockchainProvider)

    @Test
    fun testRecoverAddress() = runBlocking {
        val privateKey = "4a5ff07c23a08fd0a1066edd5d76257f768d526ab5f59c6d9704627acde28305"
        val wallet =
            walletFactory.recoverWallet(EthereumBlockchainProvider.get(), PrivateKey(privateKey))
        val actualAddress = wallet.addresses.first()
        assertEquals("0xb0a2f75bc856e225ae7bdcc11c451f5c5f165810", actualAddress.value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testGenerateAddress() = runBlocking {
        val wallet = walletFactory.createWallet(EthereumBlockchainProvider.get())
        println(wallet.privateKey.value.toHexString())
        println(wallet.addresses.first().value)
        assertTrue(EthereumAddressGenerator.validateAddress(wallet.addresses.first()))
    }
}