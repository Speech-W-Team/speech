package wtf.speech.vault.crypto.domain.generator

import wtf.speech.shared.core.domain.models.KeyPair
import wtf.speech.shared.core.domain.models.PrivateKey
import wtf.speech.shared.core.domain.models.PublicKey

actual object EthereumKeyGenerator : KeyGenerator {
    override suspend fun generateKeyPair(): KeyPair {
        TODO("Not yet implemented")
    }

    override suspend fun generatePrivateKey(): PrivateKey {
        TODO("Not yet implemented")
    }

    override suspend fun generatePublicKey(privateKey: PrivateKey): PublicKey {
        TODO("Not yet implemented")
    }
}
