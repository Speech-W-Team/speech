package wtf.speech.compass.core.graph

import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
public abstract class Screen(val id: Id) {
    @Composable
    abstract fun Content()

    @Serializable
    public data class Id(public val value: String)
}
