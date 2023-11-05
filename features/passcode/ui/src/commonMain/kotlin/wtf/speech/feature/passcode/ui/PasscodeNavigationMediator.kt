package wtf.speech.feature.passcode.ui

import androidx.compose.runtime.compositionLocalOf
import wtf.speech.compass.core.NavigationMediator
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey

interface PasscodeNavigationMediator : NavigationMediator {

    fun onEnterPasscodeSuccess(decryptedData: EncryptionSecretKey)

    fun onLogout()
}

val LocalPasscodeNavigator = compositionLocalOf<PasscodeNavigationMediator> {
    error("No LocalPasscodeNavigator provided!")
}
