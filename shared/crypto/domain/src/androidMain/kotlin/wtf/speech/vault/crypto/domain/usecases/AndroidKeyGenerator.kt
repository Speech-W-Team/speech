package wtf.speech.vault.crypto.domain.usecases

import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.jce.provider.BouncyCastleProvider
import wtf.speech.shared.core.domain.models.KeyPair
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.models.CurveType
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.Security
import java.security.spec.ECGenParameterSpec

object AndroidKeyGenerator : KeyGenerator {
    private val EC_PARAMS = CustomNamedCurves.getByName("secp256k1")

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    override suspend fun generateKeyPair(curveType: CurveType): KeyPair {
        // For this example, I'm only handling the secp256k1 curve. You can expand to others.
        if (curveType != CurveType.SECP256K1) {
            throw UnsupportedOperationException("Only SECP256K1 curve is supported in this example.")
        }

        val ecSpec = ECGenParameterSpec("secp256k1")
        val g = KeyPairGenerator.getInstance("ECDSA", "BC")
        g.initialize(ecSpec)

        val keyPair = g.generateKeyPair()
        return KeyPair(PublicKey(keyPair.public.encoded), PrivateKey(keyPair.private.encoded))
    }

    override suspend fun generatePrivateKey(): PrivateKey {
        val keyPair = generateKeyPair(CurveType.SECP256K1)
        return keyPair.privateKey
    }


    override suspend fun generatePublicKey(privateKey: PrivateKey, curveType: CurveType): PublicKey {
        if (curveType != CurveType.SECP256K1) {
            throw UnsupportedOperationException("Only SECP256K1 curve is supported in this example.")
        }

        val privKey = BigInteger(1, privateKey.value)
        val pubKey = EC_PARAMS.g.multiply(privKey)

        val pubKeyBytes = pubKey.getEncoded(true)

        return PublicKey(pubKeyBytes)
    }

}

