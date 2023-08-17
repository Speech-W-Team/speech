import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import wtf.speech.crypto.domain.CoinGeckoApi
import kotlin.test.Test

class CoinGeckoApiTest {

    // ПОКА НЕ РАБОТАЕТ, НО МЫ ОБЯЗАТЕЛЬНО ПОПРАВИМ
    @Test
    fun testGetTopCryptocurrencies() = runTest {
        // Mocking a response
        val mockedResponse = listOf(
            mapOf(
                "id" to "bitcoin",
                "name" to "Bitcoin",
                "symbol" to "btc",
                "image" to "some_url",
                "current_price" to 35000.0
            )
        )

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    when (request.url.encodedPath) { // Use encodedPath to match just the path of the URL
                        "/api/v3/coins/markets" -> {
                            val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                            respond(Json.encodeToString(mockedResponse), headers = responseHeaders)
                        }
                        else -> error("Unhandled ${request.url.fullPath}")
                    }
                }
            }
        }

        val api = CoinGeckoApi(client)
        val result = api.getTopCryptocurrencies()

        assertEquals(1, result.size)
        assertEquals("bitcoin", result[0].id)
        assertEquals("Bitcoin", result[0].name)
        assertEquals("btc", result[0].symbol)
        assertEquals("some_url", result[0].logoUrl)
//        assertEquals(BigDecimal.fromDouble(35000.0), result[0].currentPrice)
    }
}
