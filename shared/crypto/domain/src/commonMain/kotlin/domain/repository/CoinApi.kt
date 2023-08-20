package domain.repository

import domain.models.Coin
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url

/**
 * This is the class that implements the CoinRepository interface using Ktor as the HTTP client
 * It uses Coingecko API as the data source
 * It uses kotlinx.serialization to parse the JSON response into Coin objects
 */
class CoinApi(private val client: HttpClient) : CoinRepository {

    private val baseUrl = "https://rest.coinapi.io/v1"

    private val apiKey = "F86B71DE-982D-494A-8181-E8C0A3871CE3"

    /**
     * This function implements the getCoins function using coroutine and Ktor request builder
     * @return A list of Coin objects from the CoinAPI response
     */
    override suspend fun getCoins(): List<Coin> {
        val responseHttp = client.get {
            url("$baseUrl/assets")
            parameter("limit", 10)
            parameter("offset", 0)
            header("X-CoinAPI-Key", apiKey)
        }

        val response: List<Coin> = responseHttp.body()

        return response
    }
}