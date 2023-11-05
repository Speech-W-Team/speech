package wtf.speech.features.wallet.domain.usecases

import wtf.speech.core.domain.usecases.CoroutineUseCase
import wtf.speech.features.wallet.domain.models.transaction.Transaction
import wtf.speech.features.wallet.domain.repository.CryptoType
import wtf.speech.features.wallet.domain.repository.TransactionRepository

/**
 * Use case to execute a cryptocurrency transaction.
 */
class ExecuteTransactionUseCase(
    private val repository: TransactionRepository
) : CoroutineUseCase<ExecuteTransactionUseCase.Param, Boolean> {

    /**
     * Executes a cryptocurrency transaction.
     *
     * @param input Input parameters containing the cryptocurrency type and transaction details.
     * @return True if the transaction was successful, false otherwise.
     */
    override suspend fun invoke(input: Param): Boolean {
        return repository.executeTransaction(input.cryptoType, input.transaction)
    }

    /**
     * Parameters for [ExecuteTransactionUseCase].
     *
     * @property cryptoType The type of cryptocurrency.
     * @property transaction The transaction details.
     */
    data class Param(val cryptoType: CryptoType, val transaction: Transaction)
}