package wtf.speech.feature.passcode.ui

import androidx.compose.runtime.compositionLocalOf
import wtf.speech.compass.core.NavigationMediator
import wtf.speech.core.domain.models.DecryptedData

interface PasscodeNavigationMediator : NavigationMediator {

    fun onEnterPasscodeSuccess(decryptedData: DecryptedData)

    fun onLogout()
}

val LocalPasscodeNavigator = compositionLocalOf<PasscodeNavigationMediator> {
    error("No LocalPasscodeNavigator provided!")
}
