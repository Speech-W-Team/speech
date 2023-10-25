package wtf.speech.vault.app

import MainView
import android.os.Bundle
import android.util.Log
import androidx.activity.addCallback
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import wtf.speech.compass.core.rememberRouteManager
import wtf.speech.core.cryptokt.CryptoApi
import wtf.speech.feature.passcode.ui.PasscodeGraphs

class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("crypto")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backPressedDispatcher = onBackPressedDispatcher
        setContent {
            val routeManager = rememberRouteManager(PasscodeGraphs.createPasscodeGraph)
            backPressedDispatcher.addCallback {
                if (!routeManager.navigateBack()) super.onBackPressed()
            }

            MainView(routeManager)
        }

        val creator = CryptoApi()
        val array = creator.getPrivateKey()
        Log.d("MainActivity___", "$array")
    }
}
