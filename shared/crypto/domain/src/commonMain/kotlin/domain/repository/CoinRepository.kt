package domain.repository

import domain.models.Coin

/**
 * This is the interface that defines the contract for the data source
 * It has a function that returns a list of coins
 */
interface CoinRepository {
    /**
     * This function returns a list of coins from the data source
     * @return A list of Coin objects
     */
    suspend fun getCoins(): List<Coin>
}