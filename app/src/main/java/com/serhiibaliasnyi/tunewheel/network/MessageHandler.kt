package com.serhiibaliasnyi.tunewheel.network

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serhiibaliasnyi.tunewheel.data.ConnectionState
import com.serhiibaliasnyi.tunewheel.data.ParsedMessage

@Composable
fun MessageHandler(
    connectionState: ConnectionState,
    response: String?,
    parsedMessage: ParsedMessage,
    sendMessage: (String) -> Unit
) {
    var message by remember { mutableStateOf("") }
   // val message by socketViewModel.message.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (connectionState.connectionError != null) {
            Text("Cannot send message. Connection error: ${connectionState.connectionError}")
        } else if (!connectionState.isConnected) {
            Text("Cannot send message. Not connected to server.")
        } else {
           /* TextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Enter message") }
            ) */
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                sendMessage(message)
                message = ""
            }) {
                Text("Send Message")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Response: ${response ?: "No response yet"}")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Parsed Message: ${parsedMessage.message}")
       // Text("Username: ${parsedMessage.username}")
    }
}