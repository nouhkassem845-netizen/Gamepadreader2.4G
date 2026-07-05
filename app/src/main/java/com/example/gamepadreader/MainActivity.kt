package com.example.gamepadreader

import android.os.Bundle
import android.view.InputDevice
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var statusText: TextView
    private lateinit var logText: TextView
    private lateinit var scrollView: ScrollView

    private val logLines = StringBuilder()
    private var lastStickX = 0f
    private var lastStickY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        statusText = findViewById(R.id.statusText)
        logText = findViewById(R.id.logText)
        scrollView = findViewById(R.id.scrollView)

        statusText.text = "Waiting for controller... plug it in via OTG."
    }

    override fun onResume() {
        super.onResume()
        checkForConnectedGamepads()
    }

    private fun checkForConnectedGamepads() {
        val deviceIds = InputDevice.getDeviceIds()
        var found = false
        for (id in deviceIds) {
            val device = InputDevice.getDevice(id) ?: continue
            val sources = device.sources
            if ((sources and InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD ||
                (sources and InputDevice.SOURCE_JOYSTICK) ==
