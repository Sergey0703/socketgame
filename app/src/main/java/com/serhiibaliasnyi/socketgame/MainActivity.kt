package com.serhiibaliasnyi.socketgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.serhiibaliasnyi.socketgame.screens.MainScreen
import com.serhiibaliasnyi.socketgame.ui.theme.SocketgameTheme
import io.socket.emitter.Emitter
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //SocketHandler.initSocket()
        //SocketHandler.connect()

        setContent {
            SocketgameTheme {

                    MainScreen()

            }
        }
      //  SocketHandler.on("message", onNewMessage)
    }
    private val onNewMessage = Emitter.Listener { args ->
        runOnUiThread {
            val data = args[0] as JSONObject
            // Handle the received message here
        }
    }
    override fun onDestroy() {
        super.onDestroy()
      //  SocketHandler.disconnect()
    }

}

