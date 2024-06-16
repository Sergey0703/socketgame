package com.serhiibaliasnyi.socketgame
import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import java.net.URISyntaxException

object SocketManager {
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
}
