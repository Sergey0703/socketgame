package com.serhiibaliasnyi.socketgame.network

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject

/*
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject
import java.net.URISyntaxException
*/

object SocketManager {
    private const val SERVER_URL = "http://10.0.2.2:3000" // Change to your server URL
    private var socket: Socket? = null

    val isConnected = MutableStateFlow(false)
    val connectionError = MutableStateFlow<String?>(null)

    init {
        try {
            socket = IO.socket(SERVER_URL)
            socket?.on(Socket.EVENT_CONNECT) {
                isConnected.value = true
                connectionError.value = null
                Log.d("SocketManager", "Connected to server")
            }?.on(Socket.EVENT_DISCONNECT) {
                isConnected.value = false
                Log.d("SocketManager", "Disconnected from server")
            }?.on(Socket.EVENT_CONNECT_ERROR) { args ->
                isConnected.value = false
                connectionError.value = "Connection error: ${args.joinToString()}"
                Log.d("SocketManager", "Connection error: ${args.joinToString()}")
           /* }?.on(Socket.EVENT_ERROR) { args ->
                isConnected.value = false
                connectionError.value = "Error: ${args.joinToString()}"
                Log.d("SocketManager", "Error: ${args.joinToString()}") */
            }
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
    }

    fun connect() {
        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
    }

    //fun sendMessage(event: String, message: Any) {
    fun sendMessage(event: String, data: JSONObject) {
        socket?.emit(event, data)
    }

    fun setMessageListener(event: String, listener: Emitter.Listener) {
        socket?.on(event, listener)
    }

    fun removeMessageListener(event: String, listener: Emitter.Listener) {
        socket?.off(event, listener)
    }

    /*
    private const val TAG = "SocketManager"
    private lateinit var mSocket: Socket

    init {
        try {
            mSocket = IO.socket("http://10.0.2.2:3000") // Replace with your server URL
            Log.d("SocketManager", "try")
        } catch (e: URISyntaxException) {
            Log.e(TAG, e.reason ?: "Socket URI Syntax Exception")
            Log.d(TAG, e.reason ?: "Socket URI Syntax Exception")
        }
    }

    fun connect() {
        mSocket.connect()
    }

    fun disconnect() {
        mSocket.disconnect()
    }

    fun on(event: String, listener: Emitter.Listener) {
        mSocket.on(event, listener)
    }

    fun off(event: String, listener: Emitter.Listener) {
        mSocket.off(event, listener)
    }

    fun emit(event: String, data: JSONObject) {
        mSocket.emit(event, data)
    }
    */
}
