package wtf.speech.features.wallet.domain.usecases

import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.wallet.domain.models.Wallet
import wtf.speech.features.wallet.domain.repository.WalletRepository

/**
 * Use case for retrieving all user's cryptocurrency wallets.
 *
 * This use case is responsible for retrieving all user's wallets
 * using a provided repository. The use case operates on coroutines, so it should be
 * executed within a coroutine scope.
 *
 * @param repository The repository used for fetching the wallets.
 */
class GetWalletsUseCase(private val repository: WalletRepository) : CoroutineUseCase<Unit, List<Wallet>> {

    /**
     * Retrieves a wallet of the specified cryptocurrency type.
     *
     * This function invokes the repository's [WalletRepository.getWallets] function to get the wallet.
     *
     * @return Returns the list of [Wallet] of the specified type.
     */
    override suspend fun invoke(input: Unit) = repository.getWallets()
}
