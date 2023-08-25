package wtf.speech.vault.crypto.domain.usecases

import wtf.speech.vault.crypto.domain.repository.CryptoType
import wtf.speech.vault.crypto.domain.repository.TransactionRepository
import wtf.speech.shared.core.domain.usecases.CoroutineUseCase
import wtf.speech.vault.crypto.domain.models.transaction.Transaction

/**
 * Use case to execute a cryptocurrency transaction.
 */
class ExecuteTransactionUseCase(
    private val repository: TransactionRepository
) : CoroutineUseCase<ExecuteTransactionUseCase.Param, Boolean>() {

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