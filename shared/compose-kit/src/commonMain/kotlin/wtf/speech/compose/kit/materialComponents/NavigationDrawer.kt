package wtf.speech.compose.kit.materialComponents

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
): Unit{
}

@ExperimentalMaterial3Api
@Composable
fun ModalDrawerSheet(
    modifier: Modifier = Modifier,
    drawerShape: Shape = DrawerDefaults.shape,
    drawerContainerColor: Color = DrawerDefaults.containerColor,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    drawerTonalElevation: Dp = DrawerDefaults.ModalDrawerElevation,
    windowInsets: WindowInsets = DrawerDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
): Unit{
}

@ExperimentalMaterial3Api
@Composable
fun PermanentNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
): Unit{
}

@ExperimentalMaterial3Api
@Composable
fun PermanentDrawerSheet(
    modifier: Modifier = Modifier,
    drawerShape: Shape = RectangleShape,
    drawerContainerColor: Color = DrawerDefaults.containerColor,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    drawerTonalElevation: Dp = DrawerDefaults.PermanentDrawerElevation,
    windowInsets: WindowInsets = DrawerDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
): Unit{
}

@ExperimentalMaterial3Api
@Composable
fun DismissibleNavigationDrawer(
    drawerContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    gesturesEnabled: Boolean = true,
    content: @Composable () -> Unit
): Unit{
}

@ExperimentalMaterial3Api
@Composable
fun DismissibleDrawerSheet(
    modifier: Modifier = Modifier,
    drawerShape: Shape = RectangleShape,
    drawerContainerColor: Color = DrawerDefaults.containerColor,
    drawerContentColor: Color = contentColorFor(drawerContainerColor),
    drawerTonalElevation: Dp = DrawerDefaults.DismissibleDrawerElevation,
    windowInsets: WindowInsets = DrawerDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
): Unit{
}
