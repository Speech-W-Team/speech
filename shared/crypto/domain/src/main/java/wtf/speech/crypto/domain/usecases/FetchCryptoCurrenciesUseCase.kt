package wtf.speech.crypto.domain.usecases

import wtf.speech.crypto.domain.models.CryptoCurrencyInfo
import wtf.speech.crypto.domain.repository.CryptoRepository
import wtf.speech.shared.core.domain.CoroutineUseCase

/**
 * UseCase responsible for the business logic to fetch information about the top cryptocurrencies.
 *
 * @property cryptoRepository An instance of [CryptoRepository] to fetch cryptocurrency data.
 */
class FetchCryptoCurrenciesUseCase(private val cryptoRepository: CryptoRepository) : CoroutineUseCase<Int, List<CryptoCurrencyInfo>>() {

    /**
     * Fetches a list of top cryptocurrencies.
     *
     * @param input The number of cryptocurrencies to fetch.
     * @return A list of [CryptoCurrencyInfo] objects.
     */
    override suspend fun execute(input: Int): List<CryptoCurrencyInfo> {
        return cryptoRepository.fetchTopCryptoCurrencies(input)
    }
}