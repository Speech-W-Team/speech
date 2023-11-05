
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import wtf.speech.compass.core.RouteManager
import wtf.speech.vault.shared.App

@Composable
fun MainView(routeManager: RouteManager, vararg values: ProvidedValue<*>) = App(routeManager, *values)
