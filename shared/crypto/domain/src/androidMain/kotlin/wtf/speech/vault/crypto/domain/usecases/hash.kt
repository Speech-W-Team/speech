package wtf.speech.vault.crypto.domain.usecases

actual suspend fun base58Encode(data: ByteArray): String {
    return Base58.encode(data)
}

actual suspend fun base58Decode(data: String): ByteArray {
    return Base58.decode(data)
}
