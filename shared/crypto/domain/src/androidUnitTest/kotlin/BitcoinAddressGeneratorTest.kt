import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.vault.crypto.domain.models.blockchains.BitcoinBlockchain
import wtf.speech.vault.crypto.domain.models.blockchains.BitcoinBlockchainProvider
import wtf.speech.vault.crypto.domain.models.blockchains.BlockchainProvider
import wtf.speech.vault.crypto.domain.usecases.WalletFactory
import wtf.speech.vault.crypto.domain.generator.BitcoinAddressGenerator
import wtf.speech.vault.crypto.domain.generator.BitcoinKeyGenerator
import wtf.speech.vault.crypto.domain.generator.privateKeyFromWIF

class BitcoinAddressGeneratorTest {
    private val walletFactory = WalletFactory.create(BitcoinBlockchainProvider)

    @Test
    fun testRecoverAddress() = runBlocking {
        val wif = "Kx6oFQmXR2evwqnXskRzTTxJZCmPyeL6ddY3xfDa7QcZkj9HMsrR"
        val privateKey = privateKeyFromWIF(wif)
        val wallet = walletFactory.recoverWallet(BitcoinBlockchainProvider.get(true), PrivateKey(privateKey.toByteArray()))
        val actualAddress = wallet.addresses.first()
        assertEquals("1F8dT6QChMub7ipUZjHsLK6SBjt59q3yue", actualAddress.value)
    }

    @OptIn(ExperimentalStdlibApi::class)
    @Test
    fun testGenerateAddress() = runBlocking {
        val wallet = walletFactory.createWallet(BitcoinBlockchainProvider.get())
        println(wallet.privateKey.value.toHexString())
        println(wallet.addresses.first().value)

        assert(BitcoinAddressGenerator.validateAddress(wallet.addresses.first()))
    }
}