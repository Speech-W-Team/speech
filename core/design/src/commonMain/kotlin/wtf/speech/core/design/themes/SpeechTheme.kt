package wtf.speech.core.design.themes

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

val SpeechLightColorScheme = ColorScheme(
    primary = SpeechColor.Teal,
    onPrimary = SpeechColor.White,
    primaryContainer = SpeechColor.LightSkyBlue,
    onPrimaryContainer = SpeechColor.DarkNavy,
    inversePrimary = SpeechColor.BrightBlue,
    secondary = SpeechColor.Mustard,
    onSecondary = SpeechColor.White,
    secondaryContainer = SpeechColor.LightPeach,
    onSecondaryContainer = SpeechColor.DeepBrown,
    tertiary = SpeechColor.Purple,
    onTertiary = SpeechColor.White,
    tertiaryContainer = SpeechColor.Lilac,
    onTertiaryContainer = SpeechColor.DeepPurple,
    background = SpeechColor.LightMist,
    onBackground = SpeechColor.Charcoal,
    surface = SpeechColor.FaintGrey,
    onSurface = SpeechColor.Charcoal,
    surfaceVariant = SpeechColor.MistyGrey,
    onSurfaceVariant = SpeechColor.DarkStoneGrey,
    surfaceTint = SpeechColor.Teal,
    inverseSurface = SpeechColor.DarkGrey,
    inverseOnSurface = SpeechColor.LightGrey,
    error = SpeechColor.DarkRed,
    onError = SpeechColor.White,
    errorContainer = SpeechColor.PaleRed,
    onErrorContainer = SpeechColor.DarkestRed,
    outline = SpeechColor.Grey,
    outlineVariant = SpeechColor.StoneGrey,
    scrim = SpeechColor.Black,
)


val SpeechDarkColorScheme = ColorScheme(
    primary = SpeechColor.BrightSkyBlue,
    onPrimary = SpeechColor.DeepSkyBlue,
    primaryContainer = SpeechColor.DarkestTeal,
    onPrimaryContainer = SpeechColor.LightSkyBlue,
    secondary = SpeechColor.VibrantPeach,
    onSecondary = SpeechColor.Chocolate,
    secondaryContainer = SpeechColor.DarkMustard,
    onSecondaryContainer = SpeechColor.LightPeach,
    tertiary = SpeechColor.RadiantPurple,
    onTertiary = SpeechColor.Plum,
    tertiaryContainer = SpeechColor.DeepPlum,
    onTertiaryContainer = SpeechColor.Lilac,
    error = SpeechColor.VibrantRed,
    onError = SpeechColor.Maroon,
    errorContainer = SpeechColor.Crimson,
    onErrorContainer = SpeechColor.PaleRed,
    background = SpeechColor.Charcoal,
    onBackground = SpeechColor.AshyGrey,
    surface = SpeechColor.DeeperStoneGrey,
    onSurface = SpeechColor.CloudyGrey,
    surfaceVariant = SpeechColor.DarkStoneGrey,
    onSurfaceVariant = SpeechColor.StoneGrey,
    inverseOnSurface = SpeechColor.LightMist,
    inverseSurface = SpeechColor.DarkGrey,
    outline = SpeechColor.Grey,
    outlineVariant = SpeechColor.DarkStoneGrey,
    surfaceTint = SpeechColor.BrightSkyBlue,
    inversePrimary = SpeechColor.DeepBlue,
    scrim = SpeechColor.Black,
)

@Composable
fun SpeechTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        useDarkTheme -> SpeechDarkColorScheme
        else -> SpeechLightColorScheme
    }

    MaterialTheme(colorScheme, content = content)
}