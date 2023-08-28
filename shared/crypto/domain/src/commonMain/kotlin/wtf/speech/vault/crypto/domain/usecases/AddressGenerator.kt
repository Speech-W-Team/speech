package wtf.speech.vault.crypto.domain.usecases

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import org.komputing.khash.keccak.Keccak
import org.komputing.khash.keccak.KeccakParameter
import org.komputing.khash.ripemd160.Ripemd160Digest
import org.komputing.khash.sha256.Sha256
import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.models.Address
import wtf.speech.vault.crypto.domain.utils.decodeBase58
import wtf.speech.vault.crypto.domain.utils.encodeToBase58String

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


suspend fun privateKeyFromWIF(wif: String): BigInteger {
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