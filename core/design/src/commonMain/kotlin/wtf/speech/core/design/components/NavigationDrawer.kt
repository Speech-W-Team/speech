package wtf.speech.core.design.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

@ExperimentalMaterial3Api
@Composable
fun ModalNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    scrimColor: Color = DrawerDefaults.scrimColor,
    content: @Composable () -> Unit
) {
    androidx.compose.material3.ModalNavigationDrawer(
        drawerContent,
        modifier,
        drawerState,
        gesturesEnabled,
        scrimColor,
        content
    )
}

@ExperimentalMaterial3Api
@Composable
fun ModalDrawerSheetSpeech(
    modifier: Modifier = Modifier,
    drawerShape: Shape = DrawerDefaults.shape,
    drawerContainerColor: Color = DrawerDefaults.containerColor,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    drawerTonalElevation: Dp = DrawerDefaults.ModalDrawerElevation,
    windowInsets: WindowInsets = DrawerDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.material3.ModalDrawerSheet(
        modifier,
        drawerShape,
        drawerContainerColor,
        drawerContentColor,
        drawerTonalElevation,
        windowInsets,
        content
    )
}

@ExperimentalMaterial3Api
@Composable
fun PermanentNavigationDrawerSpeech(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    androidx.compose.material3.PermanentNavigationDrawer(
        drawerContent,
        modifier,
        content
    )
}

@ExperimentalMaterial3Api
@Composable
fun PermanentDrawerSheetSpeech(
    modifier: Modifier = Modifier,
    drawerShape: Shape = RectangleShape,
    drawerContainerColor: Color = DrawerDefaults.containerColor,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    drawerTonalElevation: Dp = DrawerDefaults.PermanentDrawerElevation,
    windowInsets: WindowInsets = DrawerDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.material3.PermanentDrawerSheet(
        modifier,
        drawerShape,
        drawerContainerColor,
        drawerContentColor,
        drawerTonalElevation,
        windowInsets,
        content
    )
}

@ExperimentalMaterial3Api
@Composable
fun DismissibleNavigationDrawerSpeech(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    androidx.compose.material3.DismissibleNavigationDrawer(
        drawerContent,
        modifier,
        drawerState,
        gesturesEnabled,
        content
    )
}

@ExperimentalMaterial3Api
@Composable
fun DismissibleDrawerSheetSpeech(
    modifier: Modifier = Modifier,
    drawerShape: Shape = RectangleShape,
    drawerContainerColor: Color = DrawerDefaults.containerColor,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    drawerTonalElevation: Dp = DrawerDefaults.DismissibleDrawerElevation,
    windowInsets: WindowInsets = DrawerDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.material3.DismissibleDrawerSheet(
        modifier,
        drawerShape,
        drawerContainerColor,
        drawerContentColor,
        drawerTonalElevation,
        windowInsets,
        content
    )
}
