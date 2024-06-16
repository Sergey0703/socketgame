package com.serhiibaliasnyi.socketgame.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.serhiibaliasnyi.socketgame.SocketHandler
import com.serhiibaliasnyi.socketgame.SocketViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiibaliasnyi.socketgame.ui.theme.PurpleGrey40
import com.serhiibaliasnyi.socketgame.ui.theme.PurpleGrey80
import io.socket.emitter.Emitter
import org.json.JSONObject

@Composable
fun MainScreen(socketViewModel: SocketViewModel = viewModel()) {
    val message by socketViewModel.message.collectAsState()
    //val parsedMessage by socketViewModel.parsedMessage.collectAsState()
   // val request by socketViewModel.request.collectAsState()
    var requestAccepted  by remember {
        mutableStateOf(false)
    }

    var disconnectComand  by remember {
        mutableStateOf(false)
    }
    var canAcceptRequest by remember {
        mutableStateOf(false)
    }

    var socketid by remember {
        mutableStateOf("")
    }
    var gameState by remember {
        mutableStateOf(0)
    }
    val localAnswer by remember {
        mutableStateOf(0)
    }
    var text by remember {
        mutableStateOf("")
    }
    if(message.message=="Request" && message.extra.isNotEmpty() ) canAcceptRequest=true
    if(message.message=="AcceptingTheRequest" && message.extra.isNotEmpty() && !disconnectComand){
        requestAccepted=true
        socketid=message.extra
        gameState=1
        Log.d("SocketManager", "State="+gameState)
    }
    if(message.message=="Disconnect" && message.extra.isNotEmpty() || disconnectComand){
        requestAccepted=false
        canAcceptRequest=false
        socketid=""
        gameState=0

    }
    // Listen to the socket event
 /*   DisposableEffect(Unit) {
        val listener = Emitter.Listener { args ->
            val data = args[0] as JSONObject
            message = data.getString("message")
        }
        SocketHandler.on("message", listener)
        onDispose {
            SocketHandler.mSocket.off("message", listener)
        }
    } */
    Column(modifier= Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text="Status game: $gameState",
            fontSize = 24.sp)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(White),
            contentAlignment = Alignment.Center
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Label") },
                maxLines = 1,
                modifier = Modifier.align(Alignment.TopCenter)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                //.background(Green),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text.toString(),
                    fontSize = 24.sp,
                )

                Text(
                    text = localAnswer.toString(),
                    fontSize = 24.sp
                )


            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "Message: ${message.message}")
                Text(text = "Extra: ${message.extra}")
            }
        }
        Button(
            onClick = {
                /*  val jsonObject = JSONObject()
                  jsonObject.put("message", "Hello, Server!")
                  SocketHandler.emit("message", jsonObject)  */
                socketViewModel.sendMessage("Hello, Server!", "")
                Log.d("SocketManager", "Button")
            },
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
            colors = ButtonDefaults.buttonColors(
            )
        ) {
            Text(
                text = "Send",
                fontSize = 24.sp,
                fontStyle = FontStyle.Normal,
                color = White

            )
        }

        if (gameState!=0) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            )
            {
                Text(text = "Request accepted")
                Button(
                    onClick = {

                      /*  gameState=0
                        requestAccepted=false
                        canAcceptRequest=false
                        val sock=socketid
                        socketid="" */
                        disconnectComand=true
                        //message.message=""
                        socketViewModel.sendMessage("Disconnect", socketid)
                        Log.d("SocketManager", "Disconnect")
                        Log.d("SocketManager", "gameState="+gameState)
                    },
                    shape = RoundedCornerShape(10.dp),
                    elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                    colors = ButtonDefaults.buttonColors(
                    )
                ) {
                    Text(
                        text = "Disconnect",
                        fontSize = 24.sp,
                        fontStyle = FontStyle.Normal,
                        color = Red

                    )
                }
            }


        } else {
            Button(
                onClick = {
                    /*  val jsonObject = JSONObject()
                  jsonObject.put("message", "Hello, Server!")
                  SocketHandler.emit("message", jsonObject)  */
                    disconnectComand=false
                    socketViewModel.sendMessage("Request", "")
                    Log.d("SocketManager", "Request to play")
                },
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                )
            ) {
                Text(
                    text = "Request to play",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    color = Red

                )
            }



            Button(
                enabled = canAcceptRequest,
                onClick = {
                    /*  val jsonObject = JSONObject()
                  jsonObject.put("message", "Hello, Server!")
                  SocketHandler.emit("message", jsonObject)  */
                    requestAccepted=true
                    socketid=message.extra
                    gameState=2
                    disconnectComand=false
                    socketViewModel.sendMessage("AcceptingTheRequest", message.extra)
                    Log.d("SocketManager", "Accepting the request")
                },
                shape = RoundedCornerShape(10.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                )
            ) {
                Text(
                    text = "Accepting the request",
                    fontSize = 24.sp,
                    fontStyle = FontStyle.Normal,
                    color = Green

                )
            }
        }
    }
}

@Preview
@Composable
fun MainV(showBackground: Boolean = true){
    MainScreen()
}