package com.serhiibaliasnyi.tunewheel.screens

import android.media.SoundPool
import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import com.serhiibaliasnyi.socketgame.SocketHandler
import com.serhiibaliasnyi.tunewheel.network.SocketViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.size
import androidx.media3.exoplayer.ExoPlayer
import com.serhiibaliasnyi.tunewheel.MainActivity

@Composable
fun MainScreen(sound: SoundPool?, player: ExoPlayer, playList: List<MainActivity.Music>, socketViewModel: SocketViewModel = viewModel()) {
    val infiniteTransition = rememberInfiniteTransition()
    val lightFlashing by infiniteTransition.animateFloat(
        initialValue = 0.0f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000 //6000
                0.0f at 0 with LinearOutSlowInEasing
                1.0f at 500 with LinearOutSlowInEasing
                0.0f at 1000 with LinearOutSlowInEasing
                1.0f at 1500 with LinearOutSlowInEasing
                0.0f at 2000 with LinearOutSlowInEasing
                1.0f at 2500 with LinearOutSlowInEasing
                0.0f at 3000 with LinearOutSlowInEasing
                0.0f at 6000 with LinearOutSlowInEasing
            },
            repeatMode = RepeatMode.Restart,
        )
    )

    val isConnected by socketViewModel.isConnected.collectAsState()
    val connectionError by socketViewModel.connectionError.collectAsState()
    val response by socketViewModel.response.collectAsState()
    val parsedMessage by socketViewModel.message.collectAsState()
    val message by socketViewModel.message.collectAsState()
  //  var message by viewModel.message.collectAsState()
   // var message by remember { mutableStateOf("") }

    LaunchedEffect(key1 = Unit) {
        socketViewModel.connect()
    }

    //val message by socketViewModel.message.collectAsState()
   // val connectionError by SocketManager.connectionError.collectAsState()


    var requestAccepted  by remember {
        mutableStateOf(false)
    }

    var requestDeny  by remember {
        mutableStateOf(false)
    }
    var sendRequest  by remember {
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

    Log.d("SocketManager", "message="+message.message+" extra="+message.extra+" canAcceptRequest="+canAcceptRequest)

    if(message.message=="AcceptingTheRequest" && message.extra.isNotEmpty() ){ //&& !disconnectComand
        requestAccepted=true
        socketid=message.extra
        gameState=1
        Log.d("SocketManager", "State="+gameState)
    }
    if(message.message=="DenyRequest" && message.extra.isNotEmpty() ){ //&& !disconnectComand
        sendRequest=false
        requestAccepted=false
        canAcceptRequest=false
        socketid=message.extra
        gameState=0
        Log.d("SocketManager", "DenyRequest="+gameState)
    }
    if(message.message=="Disconnect" && message.extra.isNotEmpty() ){
        sendRequest=false
        requestAccepted=false
        canAcceptRequest=false
        socketid=""
        gameState=0
        Log.d("SocketManager", "Disconnect message.message="+message.message+" disconnectComand="+disconnectComand)
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
        Row() {

            var color:Color = if(isConnected) Color.Green else Color.Red
            Canvas(modifier = Modifier
                .size(10.dp)
                //  .blur(radiusX=40.dp, radiusY=40.dp)
                , onDraw = {
                    drawCircle(color = color)
                })
            Text(
                text = "Status game: $gameState",
                fontSize = 24.sp
            )
            Canvas(modifier = Modifier
                .size(10.dp),

                onDraw = {
                    drawCircle(color = color.copy(alpha = lightFlashing))
                })
        }
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
             //   Text(text = "Message: ${message.message}")
              //  Text(text = "Extra: ${message.extra}")
                when {
                    connectionError != null -> {
                        Text("Server connection error: $connectionError")
                    }
                    !isConnected -> {
                        Text("Server is not active. Please try again later.")
                    }
                    else -> {

                        Text(text = "Message: ${message.message}")
                        Text(text = "Extra: ${message.extra}")
                        /*Text("Connected to server")
                        Spacer(modifier = Modifier.height(16.dp))
                        TextField(
                            value = message,
                            onValueChange = { message = it },
                            label = { Text("Enter message") }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = {
                            viewModel.sendMessage("message_event", message)
                            message = ""
                        }) {
                            Text("Send Message")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Response: ${response ?: "No response yet"}")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Parsed Message: ${parsedMessage.message}")
                        Text("Username: ${parsedMessage.extra}") */
                    }
                }
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
                        socketViewModel.clearMessage()
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
            if (isConnected && !canAcceptRequest) {
                Button(enabled =!sendRequest,
                      onClick = {
                        /*  val jsonObject = JSONObject()
                  jsonObject.put("message", "Hello, Server!")
                  SocketHandler.emit("message", jsonObject)  */
                        disconnectComand = false
                        sendRequest=true
                        socketViewModel.sendMessage("Request", "")
                          socketViewModel.clearMessage()
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
                        color = White

                    )
                }


            }else if(isConnected && canAcceptRequest){
                Row() {
                    Button(
                        enabled = canAcceptRequest,
                        onClick = {
                            /*  val jsonObject = JSONObject()
                  jsonObject.put("message", "Hello, Server!")
                  SocketHandler.emit("message", jsonObject)  */
                            requestAccepted = true
                            socketid = message.extra
                            gameState = 2
                            disconnectComand = false
                            socketViewModel.sendMessage("AcceptingTheRequest", message.extra)
                            socketViewModel.clearMessage()
                            Log.d("SocketManager", "Accepting the request")
                        },
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                        colors = ButtonDefaults.buttonColors(
                        )
                    ) {
                        Text(
                            text = "Ok request",
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal,
                            color = Green

                        )
                    }

                    Button(
                        enabled = canAcceptRequest,
                        onClick = {
                            /*  val jsonObject = JSONObject()
                  jsonObject.put("message", "Hello, Server!")
                  SocketHandler.emit("message", jsonObject)  */
                            requestAccepted = false
                            canAcceptRequest = false

                            socketid = message.extra
                            gameState = 0
                            // disconnectComand = false
                            socketViewModel.sendMessage("DenyRequest", message.extra)
                            socketViewModel.clearMessage()
                            Log.d("SocketManager", "Deny request")
                        },
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                        colors = ButtonDefaults.buttonColors(
                        )
                    ) {
                        Text(
                            text = "Deny request",
                            fontSize = 20.sp,
                            fontStyle = FontStyle.Normal,
                            color = Red

                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun MainV(showBackground: Boolean = true){
    //MainScreen()
}