import kotlinx.coroutines.runBlocking
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.features.wallet.domain.generator.BitcoinAddressGenerator
import wtf.speech.features.wallet.domain.generator.WalletFactory
import wtf.speech.features.wallet.domain.models.blockchains.BitcoinBlockchainProvider
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class BitcoinAddressGeneratorTest {
    private val walletFactory = WalletFactory.create(BitcoinBlockchainProvider)

    @Test
    fun testRecoverAddress() = runBlocking {
        val wif = "Kx6oFQmXR2evwqnXskRzTTxJZCmPyeL6ddY3xfDa7QcZkj9HMsrR"
        val privateKey = wtf.speech.features.wallet.domain.generator.privateKeyFromWIF(wif)
        val wallet = walletFactory.recoverWallet(
            BitcoinBlockchainProvider.get(true),
            PrivateKey(privateKey.toByteArray())
        )
        val actualAddress = wallet.addresses.first()
        assertEquals("1F8dT6QChMub7ipUZjHsLK6SBjt59q3yue", actualAddress.value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testGenerateAddress() = runBlocking {
        val wallet = walletFactory.createWallet(BitcoinBlockchainProvider.get())
        println(wallet.privateKey.value.toHexString())
        println(wallet.addresses.first().value)

        assertTrue(BitcoinAddressGenerator.validateAddress(wallet.addresses.first()))
    }
}