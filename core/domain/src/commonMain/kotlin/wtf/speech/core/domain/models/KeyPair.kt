package wtf.speech.core.domain.models

import kotlin.jvm.JvmInline

data class KeyPair(val publicKey: PublicKey, val privateKey: PrivateKey)

/**
 * Represents a public key in a cryptographic system.
 *
 * @property value The string representation of the public key.
 * @see <a href="https://en.wikipedia.org/wiki/Public-key_cryptography">Public-key Cryptography</a>
 */
@JvmInline
value class PublicKey(val value: ByteArray) {
    @OptIn(ExperimentalStdlibApi::class)
    constructor(value: String) : this(value.hexToByteArray())
}

/**
 * Represents a private key in a cryptographic system, which should be kept secure.
 *
 * @property value The string representation of the private key.
 * @see <a href="https://en.wikipedia.org/wiki/Private_key">Private Key</a>
 */
@JvmInline
value class PrivateKey(val value: ByteArray) {
    @OptIn(ExperimentalStdlibApi::class)
    constructor(value: String) : this(value.hexToByteArray())
}