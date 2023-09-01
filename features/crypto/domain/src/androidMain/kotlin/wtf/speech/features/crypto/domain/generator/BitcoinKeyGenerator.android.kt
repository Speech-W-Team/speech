package wtf.speech.features.crypto.domain.generator

import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.jce.provider.BouncyCastleProvider
import wtf.speech.core.domain.models.KeyPair
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.models.PublicKey
import wtf.speech.features.crypto.domain.models.CurveType
import java.math.BigInteger
import java.security.SecureRandom
import java.security.Security

object BitcoinKeyGenerator : KeyGenerator {
    private val parameterSpec = CurveType.SECP256K1.curveName

    private val SECP_256_K1_EC_PARAMS = CustomNamedCurves.getByName(parameterSpec)

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    override suspend fun generateKeyPair(): KeyPair {
        val privateKey = generatePrivateKey()
        val publicKey = generatePublicKey(privateKey)

        return KeyPair(
            privateKey = privateKey,
            publicKey = publicKey
        )
    }

    override suspend fun generatePrivateKey(): PrivateKey {
        val random = SecureRandom()
        val privateKey = BigInteger(256, random)
        return PrivateKey(privateKey.toByteArray())
    }

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun generatePublicKey(privateKey: PrivateKey): PublicKey {
        val privKey = BigInteger(privateKey.value.toHexString(), 16)
        val pubKey = SECP_256_K1_EC_PARAMS.g.multiply(privKey)

        return PublicKey(pubKey.getEncoded(true))
    }
}