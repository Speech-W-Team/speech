import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.usecases.BitcoinAddressGenerator
import java.security.Security

class BitcoinAddressGeneratorTest {

    @Test
    fun testGenerateAddress() {
//        val keyGenerator = AndroidKeyGenerator()

        val privateKey = PrivateKey("5HusBLvbYgnKr11hgKnAtCTcisQxj6NQD37Z6QqM3BcXdyvzffY".toByteArray())
        Security.addProvider(BouncyCastleProvider())

//        val publicKey = keyGenerator.generatePublicKey(privateKey, CurveType.SECP256K1)
//        val mockPublicKey: PublicKey = Mockito.mock(PublicKey::class.java)

        // Этот шаг может быть лишним, если у вас нет особого способа обработки PublicKey.
//        Mockito.`when`(mockPublicKey.value).thenReturn("SamplePublicKey".toByteArray())

        val addressGenerator = BitcoinAddressGenerator()

        // Вы можете использовать действительный адрес, сгенерированный из определенного PublicKey,
        // чтобы проверить правильность работы функции generateAddress.
        val expectedAddress = "1Pbogn4FcfyhvKftoLjzaS9FUxirKVuesD"


        val actualAddress = addressGenerator.generateAddress(
            PublicKey("042F8BDE4D1A07209355B4A7250A5C5128E88B84BDDC619AB7CBA8D569B240EFE4D8AC222636E5E3D6D4DBA9DDA6C9C426F788271BAB0D6840DCA87D3AA6AC62D6".toByteArray())
        ).address

        assertEquals(expectedAddress, actualAddress)
    }
}
