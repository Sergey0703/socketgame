package com.serhiibaliasnyi.tunewheel.screens

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
const val DEBOUNCE_INTERVAL_MS = 1000L
@Composable
fun DebouncedButton(onClick: () -> Unit, enabled: Boolean = true, text: String) {

    var lastClickTime =remember { mutableStateOf(0L) }
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            val currentTime = System.currentTimeMillis()
            if (currentTime - lastClickTime.value > DEBOUNCE_INTERVAL_MS) {
                lastClickTime.value = currentTime
                onClick()
            } else {
                scope.launch {
                    // Optionally, you can show a message to the user that they need to wait
                    delay(DEBOUNCE_INTERVAL_MS - (currentTime - lastClickTime.value))
                    lastClickTime.value = System.currentTimeMillis()
                }
            }
        },
        enabled = enabled
    ) {
        Text(text)
    }
}