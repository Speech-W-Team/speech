package wtf.speech.vault.crypto.domain.usecases

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import org.komputing.khash.keccak.Keccak
import org.komputing.khash.keccak.KeccakParameter
import org.komputing.khash.ripemd160.Ripemd160Digest
import org.komputing.khash.sha256.Sha256
import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.models.Address

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


/**
 * A class that implements the AddressGenerator interface for Bitcoin addresses.
 *
 * Bitcoin addresses are derived from public keys using the following steps:
 * 1. Perform SHA-256 hashing on the public key.
 * 2. Perform RIPEMD-160 hashing on the result of SHA-256.
 * 3. Add a version byte in front of the RIPEMD-160 hash. The version byte
 *    determines the network and the address type. For example, 0x00 is for
 *    mainnet P2PKH addresses, and 0x6f is for testnet P2PKH addresses.
 * 4. Perform SHA-256 hashing on the extended RIPEMD-160 result.
 * 5. Perform SHA-256 hashing on the result of the previous SHA-256 hash.
 * 6. Take the first 4 bytes of the second SHA-256 hash, this is the checksum.
 * 7. Add the 4 checksum bytes at the end of the extended RIPEMD-160 hash.
 *    This is the 25-byte binary Bitcoin address.
 * 8. Convert the result from a byte string into a base58 string using Base58Check encoding.
 *
 * This process is known as Base58Check encoding and it ensures that Bitcoin addresses are
 * human-readable, case-sensitive, and have built-in error detection.
 */
object BitcoinAddressGenerator : AddressGenerator {

    override suspend fun generateAddress(publicKey: PublicKey): Address {
        val sha256Hash = sha256(publicKey.value)
        val ripemd160Hash = ripemd160(sha256Hash)

        val preChecksumAddress = byteArrayOf(0) + ripemd160Hash
        val checksum = sha256(sha256(preChecksumAddress)).take(4).toByteArray()
        val finalAddressBytes = preChecksumAddress + checksum

        val address = finalAddressBytes.encodeToBase58String()
        return Address(address)
    }

    override suspend fun validateAddress(address: Address): Boolean {
        val decoded = address.value.decodeBase58()
        val checksum = decoded.takeLast(4).toByteArray()
        val data = decoded.dropLast(4).toByteArray()

        val calculatedChecksum = sha256(sha256(data)).take(4).toByteArray()
        return checksum.contentEquals(calculatedChecksum)
    }
}

object EthereumAddressGenerator : AddressGenerator {
    override suspend fun generateAddress(publicKey: PublicKey): Address {
        return Address("0x" + sha256(publicKey.value).takeLast(20).toByteArray().decodeToString())
    }

    override suspend fun validateAddress(address: Address): Boolean {
        return address.value.startsWith("0x") && address.value.length == 42
    }
}

object BnbAddressGenerator : AddressGenerator {
    override suspend fun generateAddress(publicKey: PublicKey): Address {
        // generate address for BNB Smart Chain
        // https://docs.binance.org/smart-chain/developer/address.html
        val pubKeyBytes = publicKey.value
        val pubKeyHash = ripemd160(sha256(pubKeyBytes))
        val addressBytes = byteArrayOf(0x41) + pubKeyHash.sliceArray(0..19)
        val checksum = sha256(sha256(addressBytes)).sliceArray(0..3)
        val address = (addressBytes + checksum).encodeToBase58String()
        return Address(address)
    }

    override suspend fun validateAddress(address: Address): Boolean {
        // validate address for BNB Smart Chain
        // https://docs.binance.org/smart-chain/developer/address.html
        try {
            val addressBytes = address.value.decodeBase58()
            if (addressBytes.size != 25 || addressBytes[0] != 0x41.toByte()) {
                return false
            }
            val checksum = addressBytes.sliceArray(21..24)
            val expectedChecksum = sha256(sha256(addressBytes.sliceArray(0..20))).sliceArray(0..3)
            return checksum.contentEquals(expectedChecksum)
        } catch (e: Exception) {
            return false
        }
    }
}

fun privateKeyFromWIF(wif: String): BigInteger{
    val decoded = wif.decodeBase58()
    return BigInteger.fromByteArray(decoded.sliceArray(1 until 33), Sign.POSITIVE)
}

fun keccak256(data: ByteArray): ByteArray {
    return Keccak.digest(data, KeccakParameter.KECCAK_512)
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

/**
 * Base58 is a way to encode addresses (or arbitrary data) as alphanumeric strings.
 * Compared to base64, this encoding eliminates ambiguities created by O0Il and potential splits from punctuation
 *
 * The basic idea of the encoding is to treat the data bytes as a large number represented using
 * base-256 digits, convert the number to be represented using base-58 digits, preserve the exact
 * number of leading zeros (which are otherwise lost during the mathematical operations on the
 * numbers), and finally represent the resulting base-58 digits as alphanumeric ASCII characters.
 *
 * This is the Kotlin implementation of base58 - it is based implementation of base58 in java
 * in bitcoinj (https://bitcoinj.github.io) - thanks to  Google Inc. and Andreas Schildbach
 *
 */
private const val ENCODED_ZERO = '1'

private const val alphabet = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz"
private val alphabetIndices by lazy {
    IntArray(128) { alphabet.indexOf(it.toChar()) }
}

/**
 * Encodes the bytes as a base58 string (no checksum is appended).
 *
 * @return the base58-encoded string
 */
fun ByteArray.encodeToBase58String(): String {

    val input = copyOf(size) // since we modify it in-place
    if (input.isEmpty()) {
        return ""
    }
    // Count leading zeros.
    var zeros = 0
    while (zeros < input.size && input[zeros].toInt() == 0) {
        ++zeros
    }
    // Convert base-256 digits to base-58 digits (plus conversion to ASCII characters)
    val encoded = CharArray(input.size * 2) // upper bound
    var outputStart = encoded.size
    var inputStart = zeros
    while (inputStart < input.size) {
        encoded[--outputStart] = alphabet[divmod(input, inputStart.toUInt(), 256.toUInt(), 58.toUInt()).toInt()]
        if (input[inputStart].toInt() == 0) {
            ++inputStart // optimization - skip leading zeros
        }
    }
    // Preserve exactly as many leading encoded zeros in output as there were leading zeros in data.
    while (outputStart < encoded.size && encoded[outputStart] == ENCODED_ZERO) {
        ++outputStart
    }
    while (--zeros >= 0) {
        encoded[--outputStart] = ENCODED_ZERO
    }
    // Return encoded string (including encoded leading zeros).
    return encoded.concatToString(outputStart, outputStart + (encoded.size - outputStart))
}

/**
 * Decodes the base58 string into a [ByteArray]
 *
 * @return the decoded data bytes
 * @throws NumberFormatException if the string is not a valid base58 string
 */
@Throws(NumberFormatException::class)
fun String.decodeBase58(): ByteArray {
    if (isEmpty()) {
        return ByteArray(0)
    }
    // Convert the base58-encoded ASCII chars to a base58 byte sequence (base58 digits).
    val input58 = ByteArray(length)
    for (i in indices) {
        val c = this[i]
        val digit = if (c.toInt() < 128) alphabetIndices[c.toInt()] else -1
        if (digit < 0) {
            throw NumberFormatException("Illegal character $c at position $i")
        }
        input58[i] = digit.toByte()
    }
    // Count leading zeros.
    var zeros = 0
    while (zeros < input58.size && input58[zeros].toInt() == 0) {
        ++zeros
    }
    // Convert base-58 digits to base-256 digits.
    val decoded = ByteArray(length)
    var outputStart = decoded.size
    var inputStart = zeros
    while (inputStart < input58.size) {
        decoded[--outputStart] = divmod(input58, inputStart.toUInt(), 58.toUInt(), 256.toUInt()).toByte()
        if (input58[inputStart].toInt() == 0) {
            ++inputStart // optimization - skip leading zeros
        }
    }
    // Ignore extra leading zeroes that were added during the calculation.
    while (outputStart < decoded.size && decoded[outputStart].toInt() == 0) {
        ++outputStart
    }
    // Return decoded data (including original number of leading zeros).
    return decoded.copyOfRange(outputStart - zeros, decoded.size)
}

/**
 * Divides a number, represented as an array of bytes each containing a single digit
 * in the specified base, by the given divisor. The given number is modified in-place
 * to contain the quotient, and the return value is the remainder.
 *
 * @param number     the number to divide
 * @param firstDigit the index within the array of the first non-zero digit
 * (this is used for optimization by skipping the leading zeros)
 * @param base       the base in which the number's digits are represented (up to 256)
 * @param divisor    the number to divide by (up to 256)
 * @return the remainder of the division operation
 */
private fun divmod(number: ByteArray, firstDigit: UInt, base: UInt, divisor: UInt): UInt {
    // this is just long division which accounts for the base of the input digits
    var remainder = 0.toUInt()
    for (i in firstDigit until number.size.toUInt()) {
        val digit = number[i.toInt()].toUByte()
        val temp = remainder * base + digit
        number[i.toInt()] = (temp / divisor).toByte()
        remainder = temp % divisor
    }
    return remainder
}
