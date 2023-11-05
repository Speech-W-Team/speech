package wtf.speech.feature.passcode.ui.create

import wtf.speech.feature.passcode.ui.BasePasscodeViewModel
import wtf.speech.feature.passcode.ui.PasscodeScreenEffect
import wtf.speech.feature.passcode.ui.PasscodeScreenState
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey

internal class CreatePasscodeViewModel(
    private val encryptionKey: ByteArray
) : BasePasscodeViewModel(PasscodeScreenState(showLogoutButton = false)) {

    override suspend fun checkPasscode(passcode: List<Int>): PasscodeScreenEffect {
        return PasscodeScreenEffect.Success(EncryptionSecretKey(encryptionKey))
    }

    override fun PasscodeScreenState.onStartBiometricAuth() = this
}
