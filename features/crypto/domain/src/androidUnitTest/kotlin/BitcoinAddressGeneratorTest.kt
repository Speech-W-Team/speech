import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.features.crypto.domain.models.Blockchain
import wtf.speech.features.crypto.domain.usecases.AndroidKeyGenerator
import wtf.speech.features.crypto.domain.usecases.WalletFactory
import wtf.speech.features.crypto.domain.usecases.generator.BitcoinAddressGenerator
import wtf.speech.features.crypto.domain.usecases.generator.privateKeyFromWIF

class BitcoinAddressGeneratorTest {
    private val walletFactory = WalletFactory.create(Blockchain.BITCOIN, AndroidKeyGenerator)

    @Test
    fun testRecoverAddress() = runBlocking {
        val wif = "Kx6oFQmXR2evwqnXskRzTTxJZCmPyeL6ddY3xfDa7QcZkj9HMsrR"
        val privateKey = privateKeyFromWIF(wif)
        val wallet =
            walletFactory.recoverWallet(Blockchain.BITCOIN, PrivateKey(privateKey.toByteArray()))
        val actualAddress = wallet.addresses.first()
        assertEquals("1F8dT6QChMub7ipUZjHsLK6SBjt59q3yue", actualAddress.value)
    }

    @Test
    fun testGenerateAddress() = runBlocking {
        val wallet = walletFactory.createWallet(Blockchain.BITCOIN)

        println(wallet.addresses.first().value)

        assert(BitcoinAddressGenerator.validateAddress(wallet.addresses.first()))
    }
}