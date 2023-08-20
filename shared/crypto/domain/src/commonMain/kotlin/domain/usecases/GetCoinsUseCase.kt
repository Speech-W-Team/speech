package domain.usecases

import domain.models.Coin
import domain.repository.CoinRepository
import wtf.speech.shared.core.domain.CoroutineUseCase

/**
 * This is the use case class that encapsulates the business logic of getting the top 30 coins by volume
 * It has a dependency on the CoinRepository interface
 * It has a function that returns a list of coins sorted by volume in descending order
 * @property coinRepository The CoinRepository instance that provides the data source
 */
class GetCoinsUseCase(private val coinRepository: CoinRepository) : CoroutineUseCase<Int, List<Coin>>() {

    /**
     * This function returns a list of coins
     * @return A list of Coin objects
     */
    override suspend operator fun invoke(input: Int): List<Coin> {
        return coinRepository.getCoins()
    }
}