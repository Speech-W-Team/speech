package wtf.speech.features.wallet.domain.usecases

import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.wallet.domain.models.transaction.Transaction
import wtf.speech.features.wallet.domain.repository.CryptoType
import wtf.speech.features.wallet.domain.repository.TransactionRepository

/**
 * Use case to fetch a list of transactions for a given cryptocurrency type.
 */
class GetTransactionsUseCase(
    private val repository: TransactionRepository
) : CoroutineUseCase<GetTransactionsUseCase.Param, List<Transaction>> {

    /**
     * Fetches a list of transactions for a given cryptocurrency type.
     *
     * @param input Input parameters containing the cryptocurrency type.
     * @return A list of transactions.
     */
    override suspend fun invoke(input: Param): List<Transaction> {
        return repository.getTransactions(input.cryptoType)
    }

    /**
     * Parameters for [GetTransactionsUseCase].
     *
     * @property cryptoType The type of cryptocurrency.
     */
    data class Param(val cryptoType: CryptoType)
}
