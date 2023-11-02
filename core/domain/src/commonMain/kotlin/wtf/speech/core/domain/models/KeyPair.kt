package wtf.speech.core.domain.models

data class KeyPair(val publicKey: PublicKey, val privateKey: PrivateKey)


data class PublicKey(val value: ByteArray) {
    @OptIn(ExperimentalStdlibApi::class)
    constructor(value: String) : this(value.hexToByteArray())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PublicKey) return false

        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }
}

/**
 * Represents a private key in a cryptographic system, which should be kept secure.
 *
 * @property value The string representation of the private key.
 * @see <a href="https://en.wikipedia.org/wiki/Private_key">Private Key</a>
 */
data class PrivateKey(val value: ByteArray) {
    @OptIn(ExperimentalStdlibApi::class)
    constructor(value: String) : this(value.hexToByteArray())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PrivateKey) return false

        if (!value.contentEquals(other.value)) return false

        return true
    }

    override fun hashCode(): Int {
        return value.contentHashCode()
    }
}
