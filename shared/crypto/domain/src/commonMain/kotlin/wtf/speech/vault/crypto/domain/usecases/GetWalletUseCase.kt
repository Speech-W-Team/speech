package wtf.speech.vault.crypto.domain.usecases

import wtf.speech.shared.core.domain.usecases.CoroutineUseCase
import wtf.speech.vault.crypto.domain.models.Wallet
import wtf.speech.vault.crypto.domain.repository.CryptoType
import wtf.speech.vault.crypto.domain.repository.WalletRepository
import kotlin.jvm.JvmInline

/**
 * Use case for retrieving a cryptocurrency wallet.
 *
 * This use case is responsible for retrieving a wallet of a specified type
 * using a provided repository. The use case operates on coroutines, so it should be
 * executed within a coroutine scope.
 *
 * @param repository The repository used for fetching the wallet.
 */
class GetWalletUseCase(private val repository: WalletRepository) : CoroutineUseCase<GetWalletUseCase.Param, Wallet>() {

    /**
     * Retrieves a wallet of the specified cryptocurrency type.
     *
     * This function invokes the repository's [WalletRepository.getWallet] function to get the wallet.
     *
     * @param input The parameters containing the cryptocurrency type. Should be an instance of [Param].
     * @return Returns the [Wallet] of the specified type.
     */
    override suspend fun invoke(input: Param): Wallet {
        return repository.getWallet(input.cryptoType)
    }

    /**
     * Parameters class for [GetWalletUseCase].
     *
     * @property cryptoType The type of cryptocurrency for which the wallet needs to be fetched.
     *
     */
    @JvmInline
    value class Param(val cryptoType: CryptoType)
}
