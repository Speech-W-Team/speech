package wtf.speech.features.wallet.domain.generator

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.refTo
import platform.CoreFoundation.CFDataCreate
import platform.CoreFoundation.CFDataRef
import platform.Foundation.NSMutableData
import platform.Foundation.dataWithLength
import platform.Security.SecKeyCopyExternalRepresentation
import platform.Security.SecKeyCopyPublicKey
import platform.Security.SecKeyCreateWithData
import platform.Security.SecRandomCopyBytes
import platform.Security.kSecRandomDefault
import wtf.speech.core.domain.models.KeyPair
import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.models.PublicKey

actual object BitcoinKeyGenerator : KeyGenerator {
    actual override suspend fun generateKeyPair(): KeyPair {
        val privateKey = generatePrivateKey()
        val publicKey = generatePublicKey(privateKey)
        return KeyPair(publicKey, privateKey)
    }

    @OptIn(ExperimentalForeignApi::class)
    actual override suspend fun generatePrivateKey(): PrivateKey {
        val privateKeyData: NSMutableData? = NSMutableData.dataWithLength(32uL)
        if (privateKeyData != null) {
            SecRandomCopyBytes(kSecRandomDefault, 32uL, privateKeyData.mutableBytes)
        }
        return PrivateKey(privateKeyData.toString().encodeToByteArray())
    }

    @OptIn(ExperimentalForeignApi::class)
    actual override suspend fun generatePublicKey(privateKey: PrivateKey): PublicKey {
        val privateKeyData = privateKey.value.toCFData()
        val privateKeyRef = SecKeyCreateWithData(privateKeyData, null, null)!!
        val publicKeyRef = SecKeyCopyPublicKey(privateKeyRef)!!
        val publicKeyData = SecKeyCopyExternalRepresentation(publicKeyRef, null)!!
        return PublicKey(publicKeyData.toString().encodeToByteArray())
    }
}

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toCFData(): CFDataRef =
    CFDataCreate(
        null,
        toUByteArray().refTo(0),
        size.toLong()
    )!!