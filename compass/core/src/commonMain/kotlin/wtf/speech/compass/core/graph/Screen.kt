package wtf.speech.compass.core.graph

import androidx.compose.runtime.Composable

public abstract class Screen(val id: Id) {
    @Composable
    abstract fun Content()

    public data class Id(public val id: String)
}
