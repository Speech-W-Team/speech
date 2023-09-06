package wtf.speech.features.wallet.domain.generator

import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.features.wallet.domain.models.ExtendedWallet
import wtf.speech.features.wallet.domain.models.blockchains.Blockchain
import wtf.speech.features.wallet.domain.models.blockchains.BlockchainProvider

/**
 * A factory class responsible for creating and recovering wallets for various blockchains.
 *
 * @param addressGenerator An instance of [AddressGenerator] used to generate blockchain addresses.
 * @param keyGenerator An instance of [KeyGenerator] used to generate key pairs.
 */
class WalletFactory(
    private val addressGenerator: AddressGenerator,
    private val keyGenerator: KeyGenerator
) {

    /**
     * Asynchronously creates a new wallet for the specified blockchain.
     *
     * This method will generate a new key pair using the SECP256K1 curve and then
     * generate the associated blockchain address for the public key.
     *
     * @param blockchain The target blockchain for which the wallet is to be created.
     * @return A [Wallet] instance with generated keys and address.
     */
    suspend fun createWallet(blockchain: Blockchain): ExtendedWallet {
        val keyPair = keyGenerator.generateKeyPair()
        val address = addressGenerator.generateAddress(keyPair.publicKey)

        return ExtendedWallet.defaultWallet(
            blockchain = blockchain,
            publicKey = keyPair.publicKey,
            privateKey = keyPair.privateKey,
            address = address
        )
    }

    /**
     * Asynchronously recovers an existing wallet for the specified blockchain using a provided private key.
     *
     * This method will generate the associated public key from the provided private key using the SECP256K1 curve,
     * then generate the associated blockchain address for the public key.
     *
     * @param blockchain The target blockchain for which the wallet is to be recovered.
     * @param privateKey The private key of the wallet to be recovered.
     * @return A [Wallet] instance with recovered keys and address.
     */
    suspend fun recoverWallet(blockchain: Blockchain, privateKey: PrivateKey): ExtendedWallet {
        val publicKey = keyGenerator.generatePublicKey(privateKey)
        val address = addressGenerator.generateAddress(publicKey)

        return ExtendedWallet.defaultWallet(
            blockchain = blockchain,
            publicKey = publicKey,
            privateKey = privateKey,
            address = address,
        )
    }

    companion object {
        fun <T : Blockchain> create(blockchainProvider: BlockchainProvider<T>): WalletFactory {
            return WalletFactory(
                addressGenerator = blockchainProvider.getAddressGenerator(),
                keyGenerator = blockchainProvider.getKeyGenerator()
            )
        }
    }
}
