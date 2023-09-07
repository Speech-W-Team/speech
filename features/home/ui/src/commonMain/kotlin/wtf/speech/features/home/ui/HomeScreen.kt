@file:OptIn(ExperimentalMaterial3Api::class)

package wtf.speech.features.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import wtf.speech.core.ui.BaseScreenContainer
import wtf.speech.core.ui.BaseViewModel

@Composable
fun HomeScreen(homeViewModel: BaseViewModel<HomeErrorState, HomeScreenState, HomeScreenAction, HomeScreenEvent, HomeScreenEffect>) {
    val state by homeViewModel.state.collectAsState()
    val effect by homeViewModel.effect.collectAsState(initial = null)

    HomeScreen(state, effect)
}

@Composable
fun HomeScreen(homeScreenState: HomeScreenState, homeScreenEffect: HomeScreenEffect?) {
    BaseScreenContainer<HomeScreenState, HomeErrorState, HomeScreenEffect>(
        onScreenState = { ContentHomeScreen(it) },
        onLoadingScreenState = {},
        onErrorScreenState = {},
        onEffect = {},
        state = homeScreenState,
        effect = homeScreenEffect,
    )
}

@Composable
private fun ContentHomeScreen(state: HomeScreenState) {
    Scaffold(
        topBar = {
            HomeTopBar(
                username = state.username,
                avatar = state.avatar,
                notificationsCount = state.notificationsCount,
            )
        },
        bottomBar = {},
        content = {},
    )
}

@Composable
fun HomeTopBar(username: String, avatar: String, notificationsCount: Int) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Image(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = username)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
        },
        actions = {},
    )
}
