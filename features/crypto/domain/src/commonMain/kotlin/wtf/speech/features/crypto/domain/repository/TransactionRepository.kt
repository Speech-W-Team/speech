package wtf.speech.features.crypto.domain.repository

import wtf.speech.features.crypto.domain.models.transaction.Transaction

/**
 * Repository interface for working with cryptocurrency transactions.
 */
interface TransactionRepository {

    /**
     * Fetches a list of transactions for a given cryptocurrency type.
     *
     * @param cryptoType The type of cryptocurrency.
     * @return A list of transactions.
     */
    suspend fun getTransactions(cryptoType: CryptoType): List<Transaction>

    /**
     * Executes a cryptocurrency transaction.
     *
     * @param cryptoType The type of cryptocurrency.
     * @param transaction The transaction details.
     * @return True if the transaction was successful, false otherwise.
     */
    suspend fun executeTransaction(cryptoType: CryptoType, transaction: Transaction): Boolean
}