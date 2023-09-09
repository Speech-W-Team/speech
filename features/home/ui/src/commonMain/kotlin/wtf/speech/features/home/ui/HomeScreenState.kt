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
    val amountFiatOnAllWalletsCurrency: String,
    val wallets: List<ContentState<WalletUI>>,
    val username: String,
    val avatar: String,
    private val notificationsCount: Int,
) : ScreenState.Content() {

    val notificationsCountText: String
        get() = if (notificationsCount > 9) {
            "9+"
        } else {
            notificationsCount.toString()
        }

    private val amountFiatOnAllWallets: BigDecimal
        get() {
            var amount = BigDecimal.ZERO

            wallets.forEach {
                when (it) {
                    is ContentState.Success -> amount = amount.add(it.data.amountFiat)
                    else -> Unit
                }
            }

            return amount
        }


    val formattedAmountFiatOnAllWallets: String
        get() = "$amountFiatOnAllWalletsCurrency${amountFiatOnAllWallets.toPlainString()}"


    companion object {
        val initial = HomeScreenState(
            amountFiatOnAllWalletsCurrency = "$",
            wallets = listOf(
                ContentState.Success(WalletUI.preview),
                ContentState.Success(WalletUI.preview),
                ContentState.Success(WalletUI.preview)
            ),
            username = "nie",
            avatar = "",
            notificationsCount = 10
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

