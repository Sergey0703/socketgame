package com.serhiibaliasnyi.tunewheel.network

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.RowScopeInstance.weight
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiibaliasnyi.tunewheel.ui.theme.Red
import com.serhiibaliasnyi.tunewheel.ui.theme.irishGroverFontFamily

@Composable
//fun Dashboard(isConnected:Boolean ,connectionError: String?, socketViewModel: SocketViewModel): MutableState<Int> {
fun Dashboard(socketViewModel: SocketViewModel): MutableState<Int> {

    val isConnected by socketViewModel.isConnected.collectAsState()
    val connectionError by socketViewModel.connectionError.collectAsState()
    val response by socketViewModel.response.collectAsState()
    val parsedMessage by socketViewModel.message.collectAsState()
    val message by socketViewModel.message.collectAsState()

    var requestAccepted  by remember {
        mutableStateOf(false)
    }

    var acceptingTheRequest by remember {
        mutableStateOf(false)
    }
    var cancelMyRequest by remember {
        mutableStateOf(false)
    }
    var cancelRequest  by remember {
        mutableStateOf(false)
    }
    var sentRequest  by remember {
        mutableStateOf(false)
    }

    var buttonSendRequest  by remember {
        mutableStateOf(false)
    }

    var buttonCancelRequest by remember {
        mutableStateOf(false)
    }
    var buttonAcceptRequest by remember {
        mutableStateOf(false)
    }
    var buttonCancelAcceptRequest by remember {
        mutableStateOf(false)
    }
    var buttonDisconnect by remember {
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

    if(message.message=="Request" && message.extra.isNotEmpty() ){
        canAcceptRequest=true
        socketid=message.extra
      //  Log.d("SocketManager", "canAcceptRequest=true")
    }
   // Log.d("SocketManager", "message="+message.message+" extra="+message.extra)
    if(message.message=="CancelRequest"){
        Log.d("SocketManager", "===========================================")
        }
    if((message.message=="CancelRequest" )  || cancelMyRequest){
        gameState=0
        disconnectComand=false
        cancelRequest=false
        cancelMyRequest=false
        requestAccepted=false
        acceptingTheRequest=false
        canAcceptRequest=false
        sentRequest=false
        socketid=""
        buttonSendRequest=true
        buttonCancelRequest=false
        buttonAcceptRequest=false
        buttonCancelAcceptRequest=false
        buttonDisconnect=false
        Log.d("SocketManager", "cancelMyRequest=---------------------------------------")
    }
    if(requestAccepted){
        canAcceptRequest=false

        Log.d("SocketManager", "canAcceptRequest=false")
    }
    if(message.message=="AcceptingTheRequest" && message.extra.isNotEmpty() ){ //&& !disconnectComand
        acceptingTheRequest=true
        socketid=message.extra

        Log.d("SocketManager", "AcceptingTheRequest")
    }
    if((message.message=="DenyRequest" && message.extra.isNotEmpty()) ||(cancelRequest) ){ //&& !disconnectComand
        gameState=0
        disconnectComand=false
        cancelRequest=false
        cancelMyRequest=false
        requestAccepted=false
        acceptingTheRequest=false
        canAcceptRequest=false
        sentRequest=false
        socketid=""
        buttonSendRequest=true
        buttonCancelRequest=false
        buttonAcceptRequest=false
        buttonCancelAcceptRequest=false
        buttonDisconnect=false
        //  gameState=0
        Log.d("SocketManager", "DenyRequest="+gameState)
    }


       if(isConnected && !sentRequest && !requestAccepted){
              buttonSendRequest=true
       //    Log.d("SocketManager", "buttonSendRequest=true")
        }
        if(isConnected && sentRequest){
            buttonSendRequest=false
            buttonCancelRequest=true
            Log.d("SocketManager", "isConnected && sentRequest")
        }
       if(isConnected && canAcceptRequest){
        buttonSendRequest=false
        buttonCancelRequest=false
        buttonAcceptRequest=true
        buttonCancelAcceptRequest=true
        buttonDisconnect=false
       //    Log.d("SocketManager", "isConnected && canAcceptRequest")
        }

       if((isConnected && requestAccepted)||(isConnected && acceptingTheRequest)){
        buttonSendRequest=false
        buttonCancelRequest=false
        buttonAcceptRequest=false
        buttonCancelAcceptRequest=false
        buttonDisconnect=true
           Log.d("SocketManager", "(isConnected && requestAccepted)||(isConnected && acceptingTheRequest)")
           if(isConnected && requestAccepted) gameState=2
           if(isConnected && acceptingTheRequest) gameState=1
       }
    if((message.message=="Disconnect" && message.extra.isNotEmpty()) || disconnectComand ){
        gameState=0
        disconnectComand=false
        cancelRequest=false
        cancelMyRequest=false
        requestAccepted=false
        acceptingTheRequest=false
        canAcceptRequest=false
        sentRequest=false
        socketid=""
        buttonSendRequest=true
        buttonCancelRequest=false
        buttonAcceptRequest=false
        buttonCancelAcceptRequest=false
        buttonDisconnect=false
        //gameState=0
        Log.d("SocketManager", "Disconnect message.message="+message.message+" disconnectComand="+disconnectComand)
    }

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



    var color: Color = if(isConnected) Color.Green else Color.Red
    Row(modifier = Modifier
       // .background(color=Red)
        .padding(0.dp)
        .fillMaxSize()) {
        Text( text="$gameState",
            modifier = Modifier
            .padding(0.dp),
            textAlign = TextAlign.Left,
            fontFamily = irishGroverFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = White,
        )
        Canvas(modifier = Modifier
            .size(10.dp),
            onDraw = {
                drawCircle(color = color.copy(alpha = lightFlashing))
            })
        Text(
            modifier = Modifier
                //.background(Yellow)
                .weight(0.25f),
            //  .fillMaxWidth(0.25f),
            text = "Player 1",
            textAlign = TextAlign.Left,
            fontFamily = irishGroverFontFamily,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = White,
        )
        when {
            connectionError != null -> {
                 Text(text="Server connection error: $connectionError",
                     textAlign = TextAlign.Left,
                  //   fontFamily = irishGroverFontFamily,
                     fontSize = 12.sp,
                     fontWeight = FontWeight.Bold,
                     color = White,

                     )
            }
            !isConnected -> {
                  Text(text="Server is not active. Please try again later.",
                      textAlign = TextAlign.Left,
                    //  fontFamily = irishGroverFontFamily,
                      fontSize = 18.sp,
                      fontWeight = FontWeight.Bold,
                      color = White,

                      )
            }
            else -> {
                //   Text("Connected to server")
               // when {
                    //isConnected && !canAcceptRequest ->{
                   if(buttonSendRequest ){
                        Button(modifier = Modifier.padding(0.dp),
                           // enabled = !sentRequest,
                            onClick = {
                                // disconnectComand = false
                                sentRequest = true
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
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Normal,
                                color = White

                            )
                        }
                    }
                    if(buttonCancelRequest){
                        Button(modifier = Modifier.padding(0.dp),
                            enabled = sentRequest,
                            onClick = {
                                // disconnectComand = false
                                cancelMyRequest = true
                                socketViewModel.sendMessage("CancelRequest", socketid)
                                socketViewModel.clearMessage()
                                Log.d("SocketManager", "Cancel Request, socket="+socketid)
                            },
                            shape = RoundedCornerShape(10.dp),
                            elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                            colors = ButtonDefaults.buttonColors(
                            )
                        ) {
                            Text(
                                text = "Cancel Request",
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Normal,
                                color = Red

                            )
                        }
                    }
                //Accept Request
                if(buttonAcceptRequest){
                    Button(modifier = Modifier.padding(0.dp),
                       // enabled = !sentRequest,
                        onClick = {
                            requestAccepted = true
                            socketid = message.extra
                          //  gameState = 2
                            socketViewModel.sendMessage("AcceptingTheRequest", message.extra)
                            socketViewModel.clearMessage()
                            Log.d("SocketManager", "Accept Request")
                        },
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                        colors = ButtonDefaults.buttonColors(
                        )
                    ) {
                        Text(
                            text = "Accept Request",
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Green
                        )
                    }
                }
                if(buttonCancelAcceptRequest){
                    Button(modifier = Modifier.padding(0.dp),
                        // enabled = !sentRequest,
                        onClick = {
                            // disconnectComand = false
                            cancelRequest = true
                            socketViewModel.sendMessage("DenyRequest", socketid)
                            socketViewModel.clearMessage()
                            Log.d("SocketManager", "Deny Accept Request")
                        },
                        shape = RoundedCornerShape(10.dp),
                        elevation = ButtonDefaults.elevatedButtonElevation(5.dp),
                        colors = ButtonDefaults.buttonColors(
                        )
                    ) {
                        Text(
                            text = "Deny Request",
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Red
                        )
                    }
                }
                //Disconnect
                if(buttonDisconnect){
                    Button(
                        onClick = {
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
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Normal,
                            color = Color.Red
                        )
                    }

                }
              //  }//when

            }
        }

    }
    return remember { mutableStateOf(gameState) }
}