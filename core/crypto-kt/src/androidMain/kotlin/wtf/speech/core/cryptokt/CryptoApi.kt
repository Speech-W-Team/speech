package wtf.speech.core.cryptokt

class CryptoApi {
    private external fun generatePrivateKey(): ByteArray

    fun getPrivateKey(): ByteArray {
        return generatePrivateKey()
    }
}
