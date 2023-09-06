package wtf.speech.features.wallet.domain.generator

import java.security.MessageDigest
import javax.crypto.spec.PBEKeySpec


//actual fun hmac512(data: ByteArray): ByteArray {
//    val keySpec = PBEKeySpec(data.toCharArray(), data, 1000, 512)
//    val messageDigest = MessageDigest.getInstance("SHA-512")
//    messageDigest.update(data)
//    return messageDigest.digest()
//}