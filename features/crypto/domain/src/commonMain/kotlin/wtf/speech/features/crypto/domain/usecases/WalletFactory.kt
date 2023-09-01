package wtf.speech.features.crypto.domain.usecases

import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.features.crypto.domain.models.Blockchain
import wtf.speech.features.crypto.domain.models.Wallet
import wtf.speech.features.crypto.domain.usecases.generator.AddressGenerator
import wtf.speech.features.crypto.domain.usecases.generator.BitcoinAddressGenerator
import wtf.speech.features.crypto.domain.usecases.generator.EthereumAddressGenerator

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
    suspend fun createWallet(blockchain: Blockchain): Wallet {
        val keyPair = keyGenerator.generateKeyPair(blockchain)
        val address = addressGenerator.generateAddress(keyPair.publicKey)

        return Wallet(
            blockchain = blockchain,
            publicKey = keyPair.publicKey,
            privateKey = keyPair.privateKey,
            addresses = mutableSetOf(address),
            tokens = mutableSetOf()
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
    suspend fun recoverWallet(blockchain: Blockchain, privateKey: PrivateKey): Wallet {
        val publicKey = keyGenerator.generatePublicKey(privateKey, blockchain)
        val address = addressGenerator.generateAddress(publicKey)

        return Wallet(
            blockchain = blockchain,
            publicKey = publicKey,
            privateKey = privateKey,
            addresses = mutableSetOf(address),
            tokens = mutableSetOf()
        )
    }

    companion object {
        fun create(blockchain: Blockchain, keyGenerator: KeyGenerator): WalletFactory {
            return WalletFactory(
                addressGenerator = when (blockchain) {
                    Blockchain.BINANCE, Blockchain.ETHEREUM -> EthereumAddressGenerator
                    Blockchain.BITCOIN -> BitcoinAddressGenerator
                    Blockchain.ETHEREUM_CLASSIC -> EthereumAddressGenerator
                    Blockchain.LITECOIN -> EthereumAddressGenerator
                },
                keyGenerator = keyGenerator
            )
        }
    }
}
