package com.serhiibaliasnyi.tunewheel.network

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serhiibaliasnyi.tunewheel.data.ParsedMessage
import io.socket.emitter.Emitter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class SocketViewModel : ViewModel() {

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    private val _connectionError = MutableStateFlow<String?>(null)
    val connectionError: StateFlow<String?> get() = _connectionError

    private val _response = MutableStateFlow<String?>(null)
    val response: StateFlow<String?> get() = _response

    private val _message = MutableStateFlow(ParsedMessage("No message", ""))
    val message: StateFlow<ParsedMessage> get() = _message


    private val responseListener = Emitter.Listener { args ->
        if (args.isNotEmpty()) {
            val responseMessage = args[0] as? JSONObject
            responseMessage?.let {
                val messageText = it.getString("message")
                val extra = it.getString("extra")
                _message.value = ParsedMessage(messageText, extra)
                _response.value = messageText // Optional, if you want to keep _response as well
            }
        }
    }
    private val messageListener = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        viewModelScope.launch {

            val receivedMessage = data.getString("message")
            val receivedExtra = data.getString("extra")
            // _message.value = data.getString("message")
            // _message.value = "Message: $receivedMessage, Extra: $receivedExtra"
            _message.value = ParsedMessage(receivedMessage, receivedExtra)
        }
    }

    init {
        observeSocketEvents()
        connect()
    }

    private fun observeSocketEvents() {
        viewModelScope.launch {
            SocketManager.isConnected.collect {
                _isConnected.value = it
            }
        }

        viewModelScope.launch {
            SocketManager.connectionError.collect {
                _connectionError.value = it
            }
        }

        // Set a message listener for receiving messages from the server
        SocketManager.setMessageListener("response_event", responseListener)
        SocketManager.setMessageListener("message", messageListener)
    }

    fun connect() {
        SocketManager.connect()
    }

    fun disconnect() {
        SocketManager.disconnect()
        SocketManager.removeMessageListener("response_event", responseListener)
    }

    //fun sendMessage(event: String, message: Any) {
    fun sendMessage(message: String, extra: String) {
        if (_isConnected.value) {
            val jsonObject = JSONObject().apply {
                put("message", message)
                put("extra", extra)
            }
            SocketManager.sendMessage("message", jsonObject)
          //  SocketManager.sendMessage(event, message)
        } else {
            _connectionError.value = "Cannot send message. Not connected to server."
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
    }
    fun clearMessage() {
        _message.value = ParsedMessage("", "")
    }
    /*
    private val _message = MutableStateFlow(ParsedMessage("No message", ""))
        // MutableStateFlow(ParsedMessage("No message", ""))
        val message: StateFlow<ParsedMessage> get() = _message
    //val message: StateFlow<String> get() = _message

   // private val _request = MutableStateFlow("No request")
   // val request: StateFlow<String> get() = _request

    private val messageListener = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        viewModelScope.launch {

            val receivedMessage = data.getString("message")
            val receivedExtra = data.getString("extra")
           // _message.value = data.getString("message")
           // _message.value = "Message: $receivedMessage, Extra: $receivedExtra"
           _message.value = ParsedMessage(receivedMessage, receivedExtra)
        }
    }

    private val answerListener = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        viewModelScope.launch {
            val answer = data.getString("answer")
            val receivedMessage = data.getString("receivedMessage")
            val receivedExtra = data.getString("receivedExtra")
            _message.value = ParsedMessage(answer, receivedExtra)

        }
    }

 /*   private val requestListener = Emitter.Listener { args ->
        val data = args[0] as JSONObject
        viewModelScope.launch {
            val idRequest = data.getString("request")
            // Update the message state with the answer from the server
            _request.value = idRequest
        }
    } */

    init {
        SocketManager.connect()
        SocketManager.on("message", messageListener)
        SocketManager.on("answer", answerListener)
       // SocketManager.on("request", requestListener)
    }

    fun sendMessage(message: String, extra: String) {
        val jsonObject = JSONObject().apply {
            put("message", message)
            put("extra", extra)
        }
        SocketManager.emit("message", jsonObject)
    }

    override fun onCleared() {
        super.onCleared()
        SocketManager.off("message", messageListener)
        SocketManager.off("answer", answerListener)
        SocketManager.disconnect()
    }

    fun clearMessage() {
        _message.value = ParsedMessage("", "")
    }

     */
}
