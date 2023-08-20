package wtf.speech.vault.shared.crypto.coins

import domain.usecases.GetCoinsUseCase
import wtf.speech.shared.core.ui.BaseViewModel

/**
 * Our ViewModel for the screen with cryptocurrencies.
 */
class CryptoViewModel(
    private val getCoinsUseCase: GetCoinsUseCase
) : BaseViewModel<CryptoErrorState, CryptoScreenState, CryptoScreenAction, CryptoScreenEvent, CryptoScreenEffect>(
    initialState = CryptoScreenState.preview
) {

    /**
     * Overrides the function for processing a user action and getting an event.
     * @param action a user action on the screen.
     * @return an event that occurred as a result of the action.
     */
    override suspend fun processAction(action: CryptoScreenAction): CryptoScreenEvent {
        return when (action) {

            is CryptoScreenAction.LoadCryptos -> {
                try {
                    CryptoScreenEvent.CryptosLoaded(listOf())
                } catch (e: Exception) {
                    CryptoScreenEvent.CryptosLoadError(e)
                }
            }

            is CryptoScreenAction.SelectCrypto -> {
                CryptoScreenEvent.CryptoSelected(action.crypto)
            }
        }
    }


    /**
     * Overrides the function for applying an event to the state and getting a new state.
     * @param event an event that occurred as a result of a user action or some other source.
     * @return a new state of the screen after applying the event.
     */
    override fun CryptoScreenState.reduce(event: CryptoScreenEvent): CryptoScreenState {
        return when (event) {

            is CryptoScreenEvent.CryptosLoaded -> {
                this.copy(cryptos = event.cryptos, isLoading = false)
            }

            is CryptoScreenEvent.CryptosLoadError -> {
                this.copy(cryptos = emptyList(), isLoading = false, error = CryptoErrorState(event.error))
            }

            is CryptoScreenEvent.CryptoSelected -> {

                this
            }
        }
    }

    /**
     * Overrides the function for handling an event and getting an effect if any.
     * @param event an event that occurred as a result of a user action or some other source.
     * @return an effect that should be performed on the screen as a result of the event, or null if no effect is needed.
     */
    override fun handleEvent(event: CryptoScreenEvent): CryptoScreenEffect? {
        return when (event) {
            is CryptoScreenEvent.CryptoSelected -> {
                CryptoScreenEffect.NavigateToCryptoDetails(event.crypto)
            }

            else -> null
        }
    }
}
