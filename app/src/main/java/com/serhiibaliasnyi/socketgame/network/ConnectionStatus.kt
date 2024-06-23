package com.serhiibaliasnyi.socketgame.network

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.serhiibaliasnyi.socketgame.data.ConnectionState

@Composable
fun ConnectionStatus(viewModel: SocketViewModel): ConnectionState {
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

    when {
        connectionError != null -> {
           // Text("Server connection error: $connectionError")
        }
        !isConnected -> {
          //  Text("Server is not active. Please try again later.")
        }
        else -> {
         //   Text("Connected to server")
        }
    }

    var color: Color = if(isConnected) Color.Green else Color.Red
    Canvas(modifier = Modifier
        .size(10.dp),
        onDraw = {
            drawCircle(color = color.copy(alpha = lightFlashing))
        })

    return ConnectionState(isConnected, connectionError)
}