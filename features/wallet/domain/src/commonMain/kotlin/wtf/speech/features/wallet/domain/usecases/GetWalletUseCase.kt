package wtf.speech.features.wallet.domain.usecases

import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.wallet.domain.models.Address
import wtf.speech.features.wallet.domain.models.Wallet
import wtf.speech.features.wallet.domain.repository.WalletRepository

/**
 * Use case for retrieving a cryptocurrency wallet.
 *
 * This use case is responsible for retrieving a wallet of a specified type
 * using a provided repository. The use case operates on coroutines, so it should be
 * executed within a coroutine scope.
 *
 * @param repository The repository used for fetching the wallet.
 */
class GetWalletUseCase internal constructor(
    private val repository: WalletRepository
) : CoroutineUseCase<GetWalletUseCase.Param, Wallet> {

    /**
     * Retrieves a wallet of the specified cryptocurrency type.
     *
     * This function invokes the repository's [WalletRepository.getWallet] function to get the wallet.
     *
     * @param input The parameters containing the cryptocurrency type. Should be an instance of [Param].
     * @return Returns the [Wallet] of the specified type.
     */
    override suspend fun invoke(input: Param): Wallet {
        return repository.getWallet(input.address)
    }

    /**
     * Parameters class for [GetWalletUseCase].
     *
     * @property address The address represents value which has associated wallet needs to be fetched.
     *
     */
    data class Param(val address: Address)
}
