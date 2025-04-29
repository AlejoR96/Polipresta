package com.poli.polipresta.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.poli.polipresta.home.MainActivity
import com.poli.polipresta.ui.theme.PoliprestaTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PoliprestaTheme {
                LoginScreen(
                    onLoginSuccess = {
                        if (!isFinishing) {
                            startActivity(MainActivity.newIntent(this))
                            finish()
                        }
                    }
                )
            }
        }
    }
}