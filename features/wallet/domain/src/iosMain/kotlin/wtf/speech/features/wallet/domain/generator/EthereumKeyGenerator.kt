package wtf.speech.features.wallet.domain.generator

import wtf.speech.core.domain.models.KeyPair
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.models.PublicKey

actual object EthereumKeyGenerator : KeyGenerator {
    actual override suspend fun generateKeyPair(): KeyPair {
        TODO("Not yet implemented")
    }

    actual override suspend fun generatePrivateKey(): PrivateKey {
        TODO("Not yet implemented")
    }

    actual override suspend fun generatePublicKey(privateKey: PrivateKey): PublicKey {
        TODO("Not yet implemented")
    }
}
