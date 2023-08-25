package wtf.speech.vault.crypto.domain.usecases

import org.bouncycastle.asn1.sec.SECNamedCurves
import org.bouncycastle.asn1.x9.X9ECParameters
import org.bouncycastle.crypto.params.ECDomainParameters
import org.bouncycastle.crypto.params.ECPrivateKeyParameters
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec
import org.bouncycastle.jce.spec.ECPublicKeySpec
import org.bouncycastle.math.ec.ECPoint
import org.bouncycastle.math.ec.FixedPointCombMultiplier
import wtf.speech.shared.core.domain.models.KeyPair
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.models.CurveType
import java.math.BigInteger
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.Security
import java.security.spec.ECGenParameterSpec

class AndroidKeyGenerator : KeyGenerator {

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

        val x9: X9ECParameters = SECNamedCurves.getByName("secp256k1")
        val domainParams = ECDomainParameters(x9.curve, x9.g, x9.n, x9.h)
        val privKey = ECPrivateKeyParameters(BigInteger(privateKey.value), domainParams)
        val q: ECPoint = FixedPointCombMultiplier().multiply(domainParams.g, privKey.d)

        // Convert to PublicKey
        val pubKeySpec = ECPublicKeySpec(q, ECNamedCurveParameterSpec(
            "secp256k1", domainParams.curve, domainParams.g, domainParams.n, domainParams.h, domainParams.seed
        )
        )
        val kf = KeyFactory.getInstance("ECDSA", "BC")
        val publicKey = kf.generatePublic(pubKeySpec)
        return PublicKey(publicKey.encoded)
    }
}

