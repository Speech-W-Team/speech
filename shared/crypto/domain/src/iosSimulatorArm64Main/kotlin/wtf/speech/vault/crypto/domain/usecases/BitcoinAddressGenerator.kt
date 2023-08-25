package wtf.speech.vault.crypto.domain.usecases

import wtf.speech.shared.core.domain.models.PublicKey
import wtf.speech.vault.crypto.domain.models.Address

actual class BitcoinAddressGenerator : AddressGenerator {
    override fun generateAddress(publicKey: PublicKey): Address {
        TODO("Not yet implemented")
    }
}