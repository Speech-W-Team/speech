package wtf.speech.features.crypto.domain.usecases.generator

import wtf.speech.core.domain.models.PublicKey
import wtf.speech.features.crypto.domain.models.Address

object EthereumAddressGenerator : AddressGenerator {

    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun generateAddress(publicKey: PublicKey): Address {
        val publicKeyBytes = publicKey.value.drop(1).toByteArray()

        val hash = keccak256(publicKeyBytes)

        val addressBytes = hash.takeLast(20).toByteArray()
        val address = "0x" + addressBytes.toHexString()
        return Address(address)
    }

    override suspend fun validateAddress(address: Address): Boolean {
        // Remove the 0x prefix and convert the address to lower case
        val cleanAddress = address.value.removePrefix("0x").lowercase()

        // Check if the address has 40 hexadecimal characters
        if (!cleanAddress.matches(Regex("[0-9a-f]{40}"))) {
            return false
        }

        // Check if the address is either all lower case or all upper case
        return true
    }
}
