package wtf.speech.vault.crypto.domain.usecases

import java.security.MessageDigest

actual fun sha256(data: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("SHA-256")
    return digest.digest(data)
}

actual fun ripemd160(data: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("RIPEMD160", "BC")
    return digest.digest(data)
}

actual fun base58Encode(data: ByteArray): String {
    return Base58.encode(data)
}

actual fun base58Decode(data: String): ByteArray {
    return Base58.decode(data)
}

actual fun keccak256(data: ByteArray): ByteArray {
    val digest = MessageDigest.getInstance("KECCAK-256", "BC")
    return digest.digest(data)
}
