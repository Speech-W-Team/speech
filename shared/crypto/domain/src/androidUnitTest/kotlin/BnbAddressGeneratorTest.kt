import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.vault.crypto.domain.models.Blockchain
import wtf.speech.vault.crypto.domain.usecases.AndroidKeyGenerator
import wtf.speech.vault.crypto.domain.usecases.BitcoinAddressGenerator
import wtf.speech.vault.crypto.domain.usecases.BnbAddressGenerator
import wtf.speech.vault.crypto.domain.usecases.WalletFactory
import wtf.speech.vault.crypto.domain.utils.encodeToBase58String

class BnbAddressGeneratorTest {
    private val walletFactory = WalletFactory(BnbAddressGenerator, AndroidKeyGenerator)

//    @OptIn(ExperimentalStdlibApi::class)
//    @Test
//    fun testRecoverAddress() = runBlocking {
//        val wallet = walletFactory.createWallet(Blockchain.BINANCE)
//        println("private: ${wallet.privateKey.value.encodeToBase58String()}")
//        println("address: ${wallet.addresses.first().value}, private: ${wallet.privateKey.value.toHexString()}")
////        assertEquals("b0a2f75Bc856e225ae7bDcc11c451f5C5F165810", wallet.addresses.first().value)
//
////        val privateKey = "4a5ff07c23a08fd0a1066edd5d76257f768d526ab5f59c6d9704627acde28305"
////        val wallet = walletFactory.recoverWallet(Blockchain.BINANCE, PrivateKey(privateKey.toByteArray()))
////        val actualAddress = wallet.addresses.first()
////        assertEquals("b0a2f75Bc856e225ae7bDcc11c451f5C5F165810", actualAddress.value)
//    }

//    @Test
//    fun testGenerateAddress() = runBlocking {
//        val wallet = walletFactory.createWallet(Blockchain.BINANCE)
//
//        assert(BitcoinAddressGenerator.validateAddress(wallet.addresses.first()))
//    }
}
