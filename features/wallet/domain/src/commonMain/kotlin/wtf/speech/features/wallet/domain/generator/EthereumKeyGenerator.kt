package wtf.speech.features.wallet.domain.generator

import wtf.speech.core.domain.models.KeyPair
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.models.PublicKey

expect object EthereumKeyGenerator : KeyGenerator {
    override suspend fun generateKeyPair(): KeyPair
    override suspend fun generatePrivateKey(): PrivateKey
    override suspend fun generatePublicKey(privateKey: PrivateKey): PublicKey
}