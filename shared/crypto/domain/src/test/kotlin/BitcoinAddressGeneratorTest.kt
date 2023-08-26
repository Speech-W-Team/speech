import kotlinx.coroutines.runBlocking
import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.vault.crypto.domain.models.Blockchain
import wtf.speech.vault.crypto.domain.models.CurveType
import wtf.speech.vault.crypto.domain.usecases.AndroidKeyGenerator
import wtf.speech.vault.crypto.domain.usecases.BitcoinAddressGenerator
import wtf.speech.vault.crypto.domain.usecases.WalletFactory
import wtf.speech.vault.crypto.domain.usecases.base58Decode
import wtf.speech.vault.crypto.domain.usecases.base58Encode
import wtf.speech.vault.crypto.domain.usecases.privateKeyFromWIF
import wtf.speech.vault.crypto.domain.usecases.ripemd160
import wtf.speech.vault.crypto.domain.usecases.sha256
import java.math.BigInteger
import java.security.Security

class BitcoinAddressGeneratorTest {
    private val walletFactory = WalletFactory(BitcoinAddressGenerator, AndroidKeyGenerator)

    @Test
    fun testRecoverAddress() = runBlocking {
        val wif = "Kx6oFQmXR2evwqnXskRzTTxJZCmPyeL6ddY3xfDa7QcZkj9HMsrR"
        val privateKey = privateKeyFromWIF(wif)
        val wallet = walletFactory.recoverWallet(Blockchain.BITCOIN, PrivateKey(privateKey.toByteArray()))
        val actualAddress = wallet.addresses.first()
        assertEquals("1F8dT6QChMub7ipUZjHsLK6SBjt59q3yue", actualAddress.value)
    }

    @Test
    fun testGenerateAddress() = runBlocking {
        val wallet = walletFactory.createWallet(Blockchain.BITCOIN)

        assert(BitcoinAddressGenerator.validateAddress(wallet.addresses.first()))
    }
}
