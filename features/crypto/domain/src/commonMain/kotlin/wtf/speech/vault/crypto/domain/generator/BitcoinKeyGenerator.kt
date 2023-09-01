package wtf.speech.vault.crypto.domain.generator

import wtf.speech.core.domain.models.KeyPair
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.models.PublicKey

expect object BitcoinKeyGenerator : KeyGenerator {
    override suspend fun generateKeyPair(): KeyPair
    override suspend fun generatePrivateKey(): PrivateKey
    override suspend fun generatePublicKey(privateKey: PrivateKey): PublicKey

}