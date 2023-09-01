package wtf.speech.vault.crypto.domain.generator

import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.models.Address
import wtf.speech.vault.crypto.domain.utils.decodeBase58
import wtf.speech.vault.crypto.domain.utils.encodeToBase58String

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