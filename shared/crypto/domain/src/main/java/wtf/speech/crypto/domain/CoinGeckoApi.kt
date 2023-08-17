package wtf.speech.crypto.domain

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import wtf.speech.crypto.domain.models.CryptoCurrencyInfo

private const val BASE_URL = "https://api.coingecko.com/api/v3"

class CoinGeckoApi(private val client: HttpClient) {
    suspend fun getTopCryptocurrencies(limit: Int = 30): List<CryptoCurrencyInfo> {
        val response: List<Map<String, String?>> = client.get("$BASE_URL/coins/markets") {
            parameter("vs_currency", "usd")
            parameter("order", "market_cap_desc")
            parameter("limit", "$limit")
            parameter("sparkline", "false")
            parameter("price_change_percentage", "24h")
            parameter("market_cap_change_percentage", "24h")
            parameter("market_dominance", "total_volumes")
            parameter("platform_version", "market_cap_change_24h")
            parameter("last_updated", "ath_change_percentage")
            parameter("local_currency", "usd")
            parameter("lang", "en")
        }.body()

        return response.map {
            CryptoCurrencyInfo(
                id = it["id"] as String,
                name = it["name"] as String,
                symbol = it["symbol"] as String,
                logoUrl = it["image"] as String,
//                currentPrice = BigDecimal.fromDouble(it["current_price"] as Double)
            )
        }
    }
}
