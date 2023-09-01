import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.vault.crypto.domain.models.Blockchain
import wtf.speech.vault.crypto.domain.usecases.AndroidKeyGenerator
import wtf.speech.vault.crypto.domain.usecases.WalletFactory
import wtf.speech.vault.crypto.domain.usecases.generator.EthereumAddressGenerator

class EthereumAddressGeneratorTest {
    private val walletFactory = WalletFactory.create(Blockchain.ETHEREUM, AndroidKeyGenerator)

    @Test
    fun testRecoverAddress() = runBlocking {
        val privateKey = "4a5ff07c23a08fd0a1066edd5d76257f768d526ab5f59c6d9704627acde28305"
        val wallet = walletFactory.recoverWallet(Blockchain.BINANCE, PrivateKey(privateKey))
        val actualAddress = wallet.addresses.first()
        assertEquals("0xb0a2f75bc856e225ae7bdcc11c451f5c5f165810", actualAddress.value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testGenerateAddress() = runBlocking {
        val wallet = walletFactory.createWallet(Blockchain.BINANCE)
        println(wallet.privateKey.value.toHexString())
        println(wallet.addresses.first().value)
        assert(EthereumAddressGenerator.validateAddress(wallet.addresses.first()))
    }
}