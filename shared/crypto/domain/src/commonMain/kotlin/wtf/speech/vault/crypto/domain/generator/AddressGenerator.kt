package wtf.speech.vault.crypto.domain.generator

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import org.komputing.khash.keccak.Keccak
import org.komputing.khash.keccak.KeccakParameter
import org.komputing.khash.ripemd160.Ripemd160Digest
import org.komputing.khash.sha256.Sha256
import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.models.Address
import wtf.speech.vault.crypto.domain.utils.decodeBase58

/**
 * Interface for generating blockchain addresses based on public keys.
 *
 * Addresses in most cryptocurrencies are derived from the public key, typically
 * using a series of cryptographic hashes and encodings. This interface provides
 * a consistent way to generate such addresses across different implementations,
 * be it for different blockchains or different methods within the same blockchain.
 */
interface AddressGenerator {

    /**
     * Generates a blockchain address from a given public key.
     *
     * @param publicKey The public key used as the basis for the generated address.
     * Typically, addresses are derived from public keys through a series of
     * cryptographic hashes and encodings. The specific process is determined
     * by the concrete implementation of this method.
     *
     * @return Address The resulting blockchain address.
     *
     * @throws InvalidPublicKeyException if the provided public key is invalid or unsuitable for address generation.
     */
    suspend fun generateAddress(publicKey: PublicKey): Address

    /**
     * Validates a blockchain address.
     *
     * @param address The address to validate.
     *
     * @return true if the address is valid, false otherwise.
     */
    suspend fun validateAddress(address: Address): Boolean
}

fun privateKeyFromWIF(wif: String): BigInteger {
    val decoded = wif.decodeBase58()
    return BigInteger.fromByteArray(decoded.sliceArray(1 until 33), Sign.POSITIVE)
}

fun wifFromPrivateKey(wif: String): BigInteger {
    val decoded = wif.decodeBase58()
    return BigInteger.fromByteArray(decoded.sliceArray(1 until 33), Sign.POSITIVE)
}

fun keccak256(data: ByteArray): ByteArray {
    return Keccak.digest(data, KeccakParameter.KECCAK_256)
}

fun sha256(data: ByteArray): ByteArray {
    return Sha256.digest(data)
}

fun ripemd160(data: ByteArray): ByteArray {
    val output = ByteArray(Ripemd160Digest.DIGEST_LENGTH)
    Ripemd160Digest().apply {
        update(data, 0, data.size)
        doFinal(output, 0)
    }

    return output
}