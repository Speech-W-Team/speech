package wtf.speech.vault.shared.crypto.coins

import domain.models.CryptoCurrency
import wtf.speech.shared.core.ui.ErrorState
import wtf.speech.shared.core.ui.ScreenAction
import wtf.speech.shared.core.ui.ScreenEffect
import wtf.speech.shared.core.ui.ScreenEvent
import wtf.speech.shared.core.ui.ScreenState

/**
 * A class for representing the state of the screen with cryptocurrencies.
 * @param cryptos a list of cryptocurrencies to display on the screen.
 * @param isLoading a flag indicating whether the data is loading or not.
 * @param error an optional error state to show in case of an error.
 */
data class CryptoScreenState(
    val cryptos: List<CryptoCurrency> = emptyList(),
    override val isLoading: Boolean = false,
    override val error: CryptoErrorState? = null,
) : ScreenState<CryptoErrorState>() {
    companion object {
        val preview = CryptoScreenState(
            cryptos = listOf(
                CryptoCurrency(name = "Bitcoin", price = 47000.0, symbol = "$", change = 20.0),
                CryptoCurrency(name = "Ethereum", price = 3200.0, symbol = "$", change = 20.0),
                CryptoCurrency(name = "Cardano", price = 2.5, symbol = "$", change = 20.0),
                CryptoCurrency(name = "Dogecoin", price = 0.25, symbol = "$", change = 20.0)
            )
        )
    }
}

/**
 * A class for representing the error state of the screen with cryptocurrencies.
 * @param error an throwable, which shows type of error
 */
class CryptoErrorState(
    error: Throwable
) : ErrorState(error)

/**
 * A sealed class for representing a user action on the screen with cryptocurrencies.
 */
sealed class CryptoScreenAction : ScreenAction {
    /**
     * A subclass for representing an action of loading the list of cryptocurrencies.
     */
    data object LoadCryptos : CryptoScreenAction()

    /**
     * A subclass for representing an action of selecting a cryptocurrency from the list.
     * @param crypto the selected cryptocurrency.
     */
    data class SelectCrypto(val crypto: CryptoCurrency) : CryptoScreenAction()
}

/**
 * A sealed class for representing an event that occurred on the screen with cryptocurrencies.
 */
sealed class CryptoScreenEvent : ScreenEvent {
    /**
     * A subclass for representing an event of successful loading of the list of cryptocurrencies.
     * @param cryptos the loaded list of cryptocurrencies.
     */
    data class CryptosLoaded(val cryptos: List<CryptoCurrency>) : CryptoScreenEvent()

    /**
     * A subclass for representing an event of error loading of the list of cryptocurrencies.
     * @param error the type of error.
     */
    data class CryptosLoadError(val error: Throwable) : CryptoScreenEvent()

    /**
     * A subclass for representing an event of selecting a cryptocurrency from the list.
     * @param crypto the selected cryptocurrency.
     */
    data class CryptoSelected(val crypto: CryptoCurrency) : CryptoScreenEvent()
}

/**
 * A sealed class for representing an effect that should be performed on the screen with cryptocurrencies.
 */
sealed class CryptoScreenEffect : ScreenEffect {
    /**
     * A subclass for representing an effect of navigating to the screen with cryptocurrency details.
     * @param crypto the cryptocurrency to show on the details screen.
     */
    data class NavigateToCryptoDetails(val crypto: CryptoCurrency) : CryptoScreenEffect()
}
