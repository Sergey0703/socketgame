package com.serhiibaliasnyi.socketgame.network

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.RowScopeInstance.weight
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serhiibaliasnyi.socketgame.ui.theme.Red
import com.serhiibaliasnyi.socketgame.ui.theme.irishGroverFontFamily

@Composable
fun Dashboard(viewModel: SocketViewModel) {
   // val connectionState = ConnectionStatus(viewModel = socketViewModel)
    val isConnected by viewModel.isConnected.collectAsState()
    val connectionError by viewModel.connectionError.collectAsState()
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

            }
        }

    }
}