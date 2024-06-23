package com.serhiibaliasnyi.socketgame.rule_screen

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.serhiibaliasnyi.socketgame.network.SocketViewModel

@Composable
fun SocketManager( socketViewModel: SocketViewModel = viewModel()) {
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


}