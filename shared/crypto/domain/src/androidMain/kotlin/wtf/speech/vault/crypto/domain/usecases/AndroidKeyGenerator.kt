package wtf.speech.vault.crypto.domain.usecases

import org.bouncycastle.crypto.ec.CustomNamedCurves
import org.bouncycastle.jce.provider.BouncyCastleProvider
import wtf.speech.shared.core.domain.models.KeyPair
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.models.Blockchain
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
        val ecSpec = ECGenParameterSpec(blockchain.curveType.curveName)
        val g = KeyPairGenerator.getInstance("ECDSA", "BC")
        g.initialize(ecSpec)

        val keyPair = g.generateKeyPair()
        return KeyPair(PublicKey(keyPair.public.encoded), PrivateKey(keyPair.private.encoded))
    }

    override suspend fun generatePrivateKey(blockchain: Blockchain): PrivateKey {
        val keyPair = generateKeyPair(blockchain)
        return keyPair.privateKey
    }

    override suspend fun generatePublicKey(privateKey: PrivateKey, blockchain: Blockchain): PublicKey {
        val publicKey = when (blockchain) {
            Blockchain.BINANCE -> generateBinancePublicKey(privateKey)
            Blockchain.BITCOIN -> generateBitcoinPublicKey(privateKey)
            Blockchain.ETHEREUM -> TODO()
            Blockchain.ETHEREUM_CLASSIC -> TODO()
            Blockchain.LITECOIN -> TODO()
        }

        return PublicKey(publicKey)
    }

    private fun generateBinancePublicKey(privateKey: PrivateKey): ByteArray {
        val privKey = BigInteger(1, privateKey.value)
        val pubKey = SECP_256_K1_EC_PARAMS.g.multiply(privKey)

        return pubKey.getEncoded(false).sliceArray(1 until 65)
    }

    private fun generateBitcoinPublicKey(privateKey: PrivateKey): ByteArray {
        val privKey = BigInteger(1, privateKey.value)
        val pubKey = SECP_256_K1_EC_PARAMS.g.multiply(privKey)

        return pubKey.getEncoded(true)
    }
}

