package wtf.speech.features.home.ui

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.ErrorState
import wtf.speech.core.ui.ScreenAction
import wtf.speech.core.ui.ScreenEffect
import wtf.speech.core.ui.ScreenEvent
import wtf.speech.core.ui.ScreenState
import wtf.speech.feature.wallet.ui.models.WalletUI

data class HomeScreenState(
    val amountFiatOnAllWallets: BigDecimal,
    val amountFiatOnAllWalletsCurrency: String,
    val wallets: List<ContentState<WalletUI>>,
    val username: String,
    val avatar: String,
    val notificationsCount: Int
) : ScreenState.Content() {

    companion object {
        val initial = HomeScreenState(
            amountFiatOnAllWallets = BigDecimal.ZERO,
            amountFiatOnAllWalletsCurrency = "USD",
            wallets = emptyList(),
            username = "nie",
            avatar = "",
            notificationsCount = 1
        )
    }
}

data class Error(override val error: HomeErrorState) : ScreenState.Error<HomeErrorState>(error)

sealed class HomeErrorState(error: Throwable) : ErrorState(error)

sealed class HomeScreenEvent : ScreenEvent {

}

sealed class HomeScreenAction : ScreenAction {

}

sealed class HomeScreenEffect : ScreenEffect {

}

