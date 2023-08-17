package wtf.speech.crypto.domain.repository

import wtf.speech.crypto.domain.models.CryptoCurrencyInfo

/**
 * Provides access to cryptocurrency data.
 */
interface CryptoRepository {

    /**
     * Fetches a list of top cryptocurrencies based on a specified limit.
     *
     * @param limit The number of cryptocurrencies to fetch. Defaults to 30.
     * @return A list of [CryptoCurrencyInfo] objects.
     */
    suspend fun fetchTopCryptoCurrencies(limit: Int = 30): List<CryptoCurrencyInfo>
}