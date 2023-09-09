@file:OptIn(ExperimentalResourceApi::class)

package wtf.speech.feature.wallet.ui.models

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import wtf.speech.core.design.components.OutlinedCardSpeech
import wtf.speech.core.design.texts.BodyLargeText
import wtf.speech.core.design.texts.BodySmallText

@Composable
fun WalletCard(onClick: () -> Unit, walletUI: WalletUI?, modifier: Modifier = Modifier) {
    OutlinedCardSpeech(
        onClick = onClick,
        modifier = modifier.width(148.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row {
                Icon(
                    painterResource("compose-multiplatform.xml"),
                    null,
                    Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                )
                BodyLargeText(walletUI?.currency.orEmpty())
            }

            BodySmallText(walletUI?.amount?.toStringExpanded().orEmpty())

            Spacer(Modifier.height(20.dp))

            BodyLargeText(walletUI?.amountFiat?.toPlainString().orEmpty())
            BodySmallText("+100%")
        }
    }
}
