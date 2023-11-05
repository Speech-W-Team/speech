package wtf.speech.vault.app

import MainView
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import wtf.speech.compass.core.rememberRouteManager
import wtf.speech.feature.passcode.ui.LocalPasscodeNavigator
import wtf.speech.feature.passcode.ui.PasscodeGraphs
import wtf.speech.feature.passcode.ui.PasscodeNavigationMediator
import wtf.speech.feature.passcode.ui.create.CreatePasscodeScreen
import wtf.speech.features.passcode.domain.models.EncryptionSecretKey

class MainActivity : AppCompatActivity() {

    init {
//        System.loadLibrary("crypto")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val graph = PasscodeGraphs.getCreatePasscodeGraph(
            CreatePasscodeScreen.CreatePasscodeExtra(byteArrayOf())
        )
        val backPressedDispatcher = onBackPressedDispatcher

        setContent {
            val routeManager = rememberRouteManager(graph).apply {
                registerGraph(PasscodeGraphs.enterPasscodeGraph)
            }
            backPressedDispatcher.addCallback(this) {
                if (!routeManager.navigateBack()) super.onBackPressed()
            }

            val passcodeMediator = object : PasscodeNavigationMediator {
                override fun onEnterPasscodeSuccess(decryptedData: EncryptionSecretKey) {
                    routeManager.switchToGraph(PasscodeGraphs.enterPasscodeGraph.id)
                }

                override fun onLogout() {
                    TODO("Not yet implemented")
                }

            }
            MainView(routeManager, LocalPasscodeNavigator provides passcodeMediator)
        }

//        val creator = CryptoApi()
//        val array = creator.getPrivateKey()
//        Log.d("MainActivity___", "$array")
    }
}

@Preview
@Composable
fun Preview() {
    MainView(
        rememberRouteManager(
            PasscodeGraphs.getCreatePasscodeGraph(
                CreatePasscodeScreen.CreatePasscodeExtra(
                    byteArrayOf()
                )
            )
        )
    )
}
