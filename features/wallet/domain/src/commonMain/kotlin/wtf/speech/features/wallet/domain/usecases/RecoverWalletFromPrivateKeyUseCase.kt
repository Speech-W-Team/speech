package wtf.speech.features.wallet.domain.usecases

import wtf.speech.core.domain.models.PrivateKey
import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.wallet.domain.generator.WalletFactory
import wtf.speech.features.wallet.domain.models.Wallet
import wtf.speech.features.wallet.domain.models.blockchains.Blockchain
import wtf.speech.features.wallet.domain.repository.WalletRepository

/**
 * An abstract class representing a use case for recovering a wallet from a private key.
 *
 * @property walletFactory The wallet factory to use.
 * @property walletRepository The wallet repository to use.
 */
abstract class RecoverWalletFromPrivateKeyUseCase(
    private val walletFactory: WalletFactory,
    private val walletRepository: WalletRepository
) : CoroutineUseCase<RecoverWalletFromPrivateKeyUseCase.Param, Wallet> {

    /**
     * Recovers a wallet from a private key and stores it in the repository.
     *
     * @param input The input parameters.
     * @return The recovered wallet.
     */
    override suspend fun invoke(input: Param): Wallet {
        val wallet = walletFactory.recoverWallet(input.blockchain, input.privateKey)
        walletRepository.storeWallet(wallet)

        return wallet
    }

    /**
     * The input parameters for the use case.
     *
     * @property blockchain The blockchain to use.
     * @property privateKey The private key to use.
     */
    data class Param(val blockchain: Blockchain, val privateKey: PrivateKey)
}
