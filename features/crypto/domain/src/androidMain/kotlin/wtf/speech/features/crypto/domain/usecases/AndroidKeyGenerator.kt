package wtf.speech.features.crypto.domain.usecases

import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.jce.provider.BouncyCastleProvider
import wtf.speech.core.domain.models.KeyPair
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.models.PublicKey
import wtf.speech.features.crypto.domain.models.Blockchain
import wtf.speech.vault.crypto.domain.models.new.CurveType
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.Security
import java.security.spec.ECGenParameterSpec

object AndroidKeyGenerator : KeyGenerator {
    private val SECP_256_K1_EC_PARAMS = CustomNamedCurves.getByName(CurveType.SECP256K1.curveName)
    private val SECP_256_R1_EC_PARAMS = CustomNamedCurves.getByName(CurveType.SECP256R1.curveName)

    init {
        Security.addProvider(BouncyCastleProvider())
    }

    override suspend fun generateKeyPair(blockchain: Blockchain): KeyPair {
        val parameterSpec = when (blockchain) {
            Blockchain.BINANCE, Blockchain.ETHEREUM, Blockchain.BITCOIN -> "secp256k1"
            else -> "secp256k1"
        }

        val keyPairGenerator = KeyPairGenerator.getInstance("EC")
        val ecSpec = ECGenParameterSpec(parameterSpec)
        keyPairGenerator.initialize(ecSpec)

        val keyPair = keyPairGenerator.generateKeyPair()
        return KeyPair(
            privateKey = PrivateKey(keyPair.private.encoded),
            publicKey = PublicKey(keyPair.public.encoded)
        )
    }

    override suspend fun generatePrivateKey(blockchain: Blockchain): PrivateKey {
        val keyPair = generateKeyPair(blockchain)

        return keyPair.privateKey
    }

    override suspend fun generatePublicKey(privateKey: PrivateKey, blockchain: Blockchain): PublicKey {
        val publicKey = when (blockchain) {
            Blockchain.BINANCE, Blockchain.ETHEREUM -> generateSecp256K1PublicKey(privateKey, false)
            Blockchain.BITCOIN -> generateSecp256K1PublicKey(privateKey, true)
            Blockchain.ETHEREUM_CLASSIC -> TODO()
            Blockchain.LITECOIN -> TODO()
        }

        return PublicKey(publicKey)
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun generateSecp256K1PublicKey(privateKey: PrivateKey, compressed: Boolean): ByteArray {
        val privKey = BigInteger(privateKey.value.toHexString(), 16)
        val pubKey = SECP_256_K1_EC_PARAMS.g.multiply(privKey)

        return pubKey.getEncoded(compressed)
    }
}
