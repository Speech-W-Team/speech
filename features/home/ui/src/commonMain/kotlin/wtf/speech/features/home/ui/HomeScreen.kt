@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalResourceApi::class,
    ExperimentalFoundationApi::class
)

package wtf.speech.features.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import wtf.speech.compass.core.Extra
import wtf.speech.compass.core.Screen
import wtf.speech.compass.core.ScreenBuilder
import wtf.speech.core.design.texts.BodyMediumText
import wtf.speech.core.design.texts.BodySmallText
import wtf.speech.core.design.texts.LabelsSmallText
import wtf.speech.core.design.texts.TitleLargeText
import wtf.speech.core.ui.BaseScreenContainer
import wtf.speech.core.ui.ContentState
import wtf.speech.core.ui.BaseViewModel
import wtf.speech.feature.wallet.ui.models.WalletCard
import wtf.speech.feature.wallet.ui.models.WalletUI

class HomeScreen internal constructor(
    override val id: String,
    private val homeViewModel: HomeViewModel
) : Screen(homeViewModel) {

    @Composable
    override fun Content() {
        HomeScreen(homeViewModel = homeViewModel)
    }

    companion object Builder : ScreenBuilder {
        const val ID = "HomeScreen"
        override val id: String = ID

        override fun build(params: Map<String, String>?, extra: Extra?): Screen {
            return HomeScreen(ID, HomeViewModel())
        }
    }
}

@Composable
fun HomeScreen(
    homeViewModel: BaseViewModel<
            HomeScreenState,
            HomeScreenAction,
            HomeScreenEvent,
            HomeScreenEffect>
) {
    val state by homeViewModel.state.collectAsState()
    val effect by homeViewModel.effect.collectAsState(initial = null)

    HomeScreen(state, effect)
}

@Composable
fun HomeScreen(homeScreenState: HomeScreenState, homeScreenEffect: HomeScreenEffect?) {
    BaseScreenContainer<HomeScreenState, HomeErrorState, HomeScreenEffect>(
        onScreenState = { ScaffoldHomeScreen(it) },
        onLoadingScreenState = {},
        onErrorScreenState = {},
        onEffect = {},
        state = homeScreenState,
        effect = homeScreenEffect,
    )
}

@Composable
private fun ScaffoldHomeScreen(state: HomeScreenState) {
    Scaffold(
        topBar = {
            HomeTopBar(
                username = state.username,
                notificationsCount = state.notificationsCountText,
            )
        },
        content = { insets -> ContentHomeScreen(insets, state) },
    )
}

@Composable
fun HomeTopBar(username: String, notificationsCount: String) {
    TopAppBar(
        title = { UserProfileTitle(username) },
        actions = {
            ScanButton()

            NotificationsButton({}, notificationsCount)
        },
    )
}

@Composable
private fun ContentHomeScreen(paddingValues: PaddingValues, state: HomeScreenState) {
    LazyColumn(contentPadding = paddingValues, verticalArrangement = Arrangement.spacedBy(16.dp)) {
        stickyHeader { AmountHeader(state) }
        item { Wallets(state.wallets) }
    }
}

@Composable
private fun AmountHeader(state: HomeScreenState) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            TitleLargeText(
                text = state.formattedAmountFiatOnAllWallets,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp, vertical = 36.dp)
            )

            OperationItem(
                onClick = {},
                icon = painterResource("ic_arrow_up_circle.xml"),
                title = "Send"
            )
            OperationItem(
                onClick = {},
                icon = painterResource("ic_arrow_down_circle.xml"),
                title = "Receive"
            )
        }

        Divider(Modifier.fillMaxWidth())
    }

}

@Composable
fun Wallets(items: List<ContentState<WalletUI>>) {
    Column {
        TitleLargeText("Wallets", modifier = Modifier.padding(start = 16.dp))

        LazyRow(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 12.dp)
        ) {
            items(items) { item ->
                when (item) {
                    is ContentState.Error<*, *> -> TODO()
                    else -> WalletCard({}, item.item)
                }
            }
        }
    }
}

@Composable
private fun UserProfileTitle(username: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource("compose-multiplatform.xml"),
            contentDescription = null,
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(8.dp))
        BodySmallText(text = username)
        Spacer(modifier = Modifier.width(4.dp))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun ScanButton() {
    IconButton(onClick = {}) {
        Icon(
            painterResource("ic_qr_maximize.xml"),
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun NotificationsButton(onClick: () -> Unit, notificationsCount: String) {
    IconButton(onClick = onClick) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.size(48.dp)) {
            Icon(
                painterResource("ic_notifications_bell.xml"),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            LabelsSmallText(
                text = notificationsCount,
                color = MaterialTheme.colorScheme.onErrorContainer,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(MaterialTheme.colorScheme.errorContainer, CircleShape)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
private fun OperationItem(onClick: () -> Unit, icon: Painter, title: String) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(end = 16.dp)
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .background(Color.Gray, RoundedCornerShape(16.dp))
        ) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        }

        BodyMediumText(text = title, modifier = Modifier.padding(top = 4.dp))
    }
}
