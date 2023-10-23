package wtf.speech.vault.app

import MainView
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import wtf.speech.core.cryptokt.CryptoApi

class MainActivity : AppCompatActivity() {

    init {
        System.loadLibrary("crypto")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainView()
        }

        val creator = CryptoApi()
        val array = creator.getPrivateKey()
        Log.d("MainActivity___", "$array")
    }
}
